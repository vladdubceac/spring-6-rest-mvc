package md.vladdubceac.learning.spring6restmvc.services;

import md.vladdubceac.learning.spring6restmvc.model.Beer;

import java.util.List;
import java.util.UUID;

public interface BeerService {
    Beer getBeerById(UUID id);

    List<Beer> listBeers();
}
