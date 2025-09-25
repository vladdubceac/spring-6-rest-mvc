package md.vladdubceac.learning.spring6restmvc.services;

import lombok.extern.slf4j.Slf4j;
import md.vladdubceac.learning.spring6restmvc.model.Beer;
import md.vladdubceac.learning.spring6restmvc.model.BeerStyle;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

    private Map<UUID, Beer> beerMap;

    public BeerServiceImpl() {
        this.beerMap = new HashMap<>();

        Beer beer1 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Chisinau")
                .beerStyle(BeerStyle.WHEAT)
                .upc("123456")
                .price(new BigDecimal("2.99"))
                .quantityOnHand(123)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer beer2 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Vitanta")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("123456")
                .price(new BigDecimal("2.49"))
                .quantityOnHand(234)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer beer3 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Orasul meu")
                .beerStyle(BeerStyle.WHEAT)
                .upc("2345")
                .price(new BigDecimal("2.09"))
                .quantityOnHand(345)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        beerMap.put(beer1.getId(), beer1);
        beerMap.put(beer2.getId(), beer2);
        beerMap.put(beer3.getId(), beer3);
    }

    @Override
    public Beer getBeerById(UUID id) {

        log.debug("Get Beer by ID - in service. ID = " + id.toString());

        return beerMap.get(id);
    }

    @Override
    public List<Beer> listBeers(){
        return new ArrayList<>(beerMap.values());
    }
}
