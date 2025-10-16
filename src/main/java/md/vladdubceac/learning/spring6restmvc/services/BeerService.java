package md.vladdubceac.learning.spring6restmvc.services;

import md.vladdubceac.learning.spring6restmvc.model.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    Optional<BeerDTO> getBeerById(UUID id);

    List<BeerDTO> listBeers();

    BeerDTO saveNewBeer(BeerDTO beer);

    Optional<BeerDTO> updateById(UUID id, BeerDTO beer);

    boolean delete(UUID id);

    void patchById(UUID id, BeerDTO beer);
}
