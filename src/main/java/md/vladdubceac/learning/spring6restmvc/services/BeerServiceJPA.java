package md.vladdubceac.learning.spring6restmvc.services;

import lombok.RequiredArgsConstructor;
import md.vladdubceac.learning.spring6restmvc.entities.Beer;
import md.vladdubceac.learning.spring6restmvc.mappers.BeerMapper;
import md.vladdubceac.learning.spring6restmvc.model.BeerDTO;
import md.vladdubceac.learning.spring6restmvc.repositories.BeerRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
    public BeerDTO updateById(UUID id, BeerDTO beerDTO) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }

    @Override
    public void patchById(UUID id, BeerDTO beer) {

    }
}
