package md.vladdubceac.learning.spring6restmvc.services;

import lombok.RequiredArgsConstructor;
import md.vladdubceac.learning.spring6restmvc.entities.Beer;
import md.vladdubceac.learning.spring6restmvc.mappers.BeerMapper;
import md.vladdubceac.learning.spring6restmvc.model.BeerDTO;
import md.vladdubceac.learning.spring6restmvc.repositories.BeerRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        Beer beer = beerRepository.findById(id).orElse(null);
        return Optional.ofNullable(beerMapper.beerToBeerDTO(beer));
    }

    @Override
    public List<BeerDTO> listBeers() {
        return beerRepository.findAll()
                .stream()
                .map(beerMapper::beerToBeerDTO)
                .collect(Collectors.toList());
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
