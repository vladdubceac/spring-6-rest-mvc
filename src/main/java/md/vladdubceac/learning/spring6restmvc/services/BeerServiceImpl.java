package md.vladdubceac.learning.spring6restmvc.services;

import lombok.extern.slf4j.Slf4j;
import md.vladdubceac.learning.spring6restmvc.model.Beer;
import md.vladdubceac.learning.spring6restmvc.model.BeerStyle;
import md.vladdubceac.learning.spring6restmvc.utils.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

    private final Map<UUID, Beer> beerMap;

    public BeerServiceImpl() {
        this.beerMap = new HashMap<>();

        Beer beer1 = Beer.builder().id(UUID.randomUUID()).version(1).beerName("Chisinau").beerStyle(BeerStyle.WHEAT).upc("123456").price(new BigDecimal("2.99")).quantityOnHand(123).createdDate(LocalDateTime.now()).updatedDate(LocalDateTime.now()).build();

        Beer beer2 = Beer.builder().id(UUID.randomUUID()).version(1).beerName("Vitanta").beerStyle(BeerStyle.PALE_ALE).upc("123456").price(new BigDecimal("2.49")).quantityOnHand(234).createdDate(LocalDateTime.now()).updatedDate(LocalDateTime.now()).build();

        Beer beer3 = Beer.builder().id(UUID.randomUUID()).version(1).beerName("Orasul meu").beerStyle(BeerStyle.WHEAT).upc("2345").price(new BigDecimal("2.09")).quantityOnHand(345).createdDate(LocalDateTime.now()).updatedDate(LocalDateTime.now()).build();

        beerMap.put(beer1.getId(), beer1);
        beerMap.put(beer2.getId(), beer2);
        beerMap.put(beer3.getId(), beer3);
    }

    @Override
    public Optional<Beer> getBeerById(UUID id) {

        log.debug("Get Beer by ID - in service. ID = " + id.toString());

        return Optional.of(beerMap.get(id));
    }

    @Override
    public List<Beer> listBeers() {
        return new ArrayList<>(beerMap.values());
    }

    @Override
    public Beer saveNewBeer(Beer beer) {
        Beer saved = Beer.builder().id(UUID.randomUUID()).beerName(beer.getBeerName()).version(Optional.ofNullable(beer.getVersion()).orElse(1)).upc(beer.getUpc()).price(beer.getPrice()).quantityOnHand(beer.getQuantityOnHand()).beerStyle(beer.getBeerStyle()).createdDate(LocalDateTime.now()).updatedDate(LocalDateTime.now()).build();
        beerMap.put(saved.getId(), saved);
        return saved;
    }

    @Override
    public Beer updateById(UUID id, Beer beer) {
        Beer existing = beerMap.get(id);
        existing.setBeerName(beer.getBeerName());
        existing.setPrice(beer.getPrice());
        existing.setUpc(beer.getUpc());
        existing.setQuantityOnHand(beer.getQuantityOnHand());
        existing.setBeerStyle(beer.getBeerStyle());
        if (beer.getVersion() != null) {
            existing.setVersion(beer.getVersion());
        }
        existing.setUpdatedDate(LocalDateTime.now());
//        beerMap.put(id, existing);
        return existing;
    }

    @Override
    public void delete(UUID id) {
        beerMap.remove(id);
    }

    @Override
    public void patchById(UUID id, Beer beer) {
        Beer existingBeer = beerMap.get(id);
        if (existingBeer == null) {
            return;
        }

        if (StringUtils.hasText(beer.getBeerName())) {
            existingBeer.setBeerName(beer.getBeerName());
        }

        if (beer.getBeerStyle() != null) {
            existingBeer.setBeerStyle(beer.getBeerStyle());
        }

        if (beer.getPrice() != null) {
            existingBeer.setPrice(beer.getPrice());
        }

        if(beer.getVersion()!=null){
            existingBeer.setVersion(beer.getVersion());
        }

        if(beer.getQuantityOnHand()!=null) {
            existingBeer.setQuantityOnHand(beer.getQuantityOnHand());
        }

        if(StringUtils.hasText(beer.getUpc())){
            existingBeer.setUpc(beer.getUpc());
        }

        if(beer.getCreatedDate()!=null) {
            existingBeer.setCreatedDate(beer.getCreatedDate());
        }

        existingBeer.setUpdatedDate(LocalDateTime.now());
    }
}
