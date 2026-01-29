package md.vladdubceac.learning.spring6restmvc.services;

import lombok.RequiredArgsConstructor;
import md.vladdubceac.learning.spring6restmvc.entities.Beer;
import md.vladdubceac.learning.spring6restmvc.mappers.BeerMapper;
import md.vladdubceac.learning.spring6restmvc.model.BeerDTO;
import md.vladdubceac.learning.spring6restmvc.model.BeerStyle;
import md.vladdubceac.learning.spring6restmvc.repositories.BeerRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {
    private final static int DEFAULT_PAGE = 0;
    private final static int DEFAULT_PAGE_SIZE = 25;
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        Beer beer = beerRepository.findById(id).orElse(null);
        return Optional.ofNullable(beerMapper.beerToBeerDTO(beer));
    }

    @Override
    public Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventory, Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

        Page<Beer> beerPage;

        if (StringUtils.hasText(beerName) && beerStyle == null) {
            beerPage = listBeersByName(beerName, pageRequest);
        } else if (!StringUtils.hasText(beerName) && beerStyle != null) {
            beerPage = listBeersByStyle(beerStyle, pageRequest);
        } else if (StringUtils.hasText(beerName) && beerStyle != null) {
            beerPage = listBeersByNameAndStyle(beerName, beerStyle, pageRequest);
        } else {
            beerPage = beerRepository.findAll(pageRequest);
        }

        if (showInventory != null && !showInventory) {
            beerPage.forEach(beer -> beer.setQuantityOnHand(null));
        }

        return beerPage.map(beerMapper::beerToBeerDTO);
    }

    public PageRequest buildPageRequest(Integer pageNumber, Integer pageSize) {
        int queryPageNumber;
        int queryPageSize;

        if (pageNumber != null && pageNumber > 0) {
            queryPageNumber = pageNumber - 1;
        } else {
            queryPageNumber = DEFAULT_PAGE;
        }

        if (pageSize == null) {
            queryPageSize = DEFAULT_PAGE_SIZE;
        } else {
            if (pageSize > 1000) {
                queryPageSize = 1000;
            } else {
                queryPageSize = pageSize;
            }
        }

        Sort sort = Sort.by(Sort.Order.asc("beerName"));

        return PageRequest.of(queryPageNumber, queryPageSize, sort);
    }

    public Page<Beer> listBeersByName(String beerName, Pageable pageable) {
        return beerRepository.findAllByBeerNameIsLikeIgnoreCase("%" + beerName + "%", pageable);
    }

    public Page<Beer> listBeersByStyle(BeerStyle beerStyle, Pageable pageable) {
        return beerRepository.findAllByBeerStyle(beerStyle, pageable);
    }

    public Page<Beer> listBeersByNameAndStyle(String beerName, BeerStyle beerStyle, Pageable pageable) {
        return beerRepository.findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle("%" + beerName + "%", beerStyle, pageable);
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beerDTO) {
        Beer beer = beerMapper.beerDtoToBeer(beerDTO);
        beer = beerRepository.save(beer);
        return beerMapper.beerToBeerDTO(beer);
    }

    @Override
    public Optional<BeerDTO> updateById(UUID id, BeerDTO beerDTO) {
        Optional<Beer> result = beerRepository.findById(id);
        BeerDTO updatedDTO = null;
        if (result.isPresent()) {
            Beer founded = result.get();
            founded.setBeerStyle(beerDTO.getBeerStyle());
            founded.setBeerName(beerDTO.getBeerName());
            founded.setPrice(beerDTO.getPrice());
            founded.setUpc(beerDTO.getUpc());
            founded.setQuantityOnHand(beerDTO.getQuantityOnHand());
            founded.setVersion(beerDTO.getVersion());
            founded.setCreatedDate(beerDTO.getCreatedDate());
            founded.setUpdatedDate(Optional.ofNullable(beerDTO.getUpdatedDate()).orElse(LocalDateTime.now()));
            updatedDTO = beerMapper.beerToBeerDTO(beerRepository.save(founded));
        }
        return Optional.ofNullable(updatedDTO);
    }

    @Override
    public boolean delete(UUID id) {
        if (beerRepository.existsById(id)) {
            beerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<BeerDTO> patchById(UUID id, BeerDTO beer) {
        AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference<>();
        beerRepository.findById(id).ifPresentOrElse(found -> {
                    found.setPrice(beer.getPrice());
                    found.setBeerStyle(beer.getBeerStyle());
                    found.setBeerName(beer.getBeerName());
                    found.setUpc(beer.getUpc());
                    found.setQuantityOnHand(beer.getQuantityOnHand());
                    found.setVersion(beer.getVersion());
                    found.setCreatedDate(beer.getCreatedDate());
                    found.setUpdatedDate(Optional.ofNullable(found.getUpdatedDate()).orElse(LocalDateTime.now()));
                    atomicReference.set(Optional.of(beerMapper.beerToBeerDTO(beerRepository.save(found))));
                },
                () -> atomicReference.set(Optional.empty()));
        return atomicReference.get();
    }
}
