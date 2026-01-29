package md.vladdubceac.learning.spring6restmvc.repositories;

import jakarta.validation.ConstraintViolationException;
import md.vladdubceac.learning.spring6restmvc.bootstrap.BootstrapData;
import md.vladdubceac.learning.spring6restmvc.entities.Beer;
import md.vladdubceac.learning.spring6restmvc.model.BeerStyle;
import md.vladdubceac.learning.spring6restmvc.services.BeerCsvServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@Import({BootstrapData.class, BeerCsvServiceImpl.class})
class BeerRepositoryTest {
    @Autowired
    BeerRepository beerRepository;

    @Test
    void testGetBeerListByName(){
        Page<Beer> page = beerRepository.findAllByBeerNameIsLikeIgnoreCase("%IPA%", null);

        assertThat(page.getContent().size()).isGreaterThanOrEqualTo(1);
        assertThat(page.getContent().size()).isEqualTo(336);
    }

    @Test
    void testGetBeerListByStyle(){
        Page<Beer> page = beerRepository.findAllByBeerStyle(BeerStyle.IPA, null);

        assertThat(page.getContent().size()).isGreaterThanOrEqualTo(1);
        assertThat(page.getContent().size()).isEqualTo(547);
    }

    @Test
    void testGetBeerListByNameAndStyle(){
        Page<Beer> page = beerRepository.findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle("%big%",BeerStyle.IPA, null);

        assertThat(page.getContent().size()).isGreaterThanOrEqualTo(1);
        assertThat(page.getContent().size()).isEqualTo(7);
    }

    @Test
    void testSaveBeerNameTooLong() {
        assertThrows(ConstraintViolationException.class, () ->
                {
                    beerRepository.save(Beer.builder()
                            .beerName("My test beer 1234567890 My test beer 1234567890 1234567890 1234567890")
                            .beerStyle(BeerStyle.WHEAT)
                            .upc("123")
                            .price(new BigDecimal("11.99"))
                            .build());
                    beerRepository.flush();
                }
        );
    }

    @Test
    void testSaveBeer() {
        Beer savedBeer = beerRepository.save(Beer.builder()
                .beerName("My test beer")
                .beerStyle(BeerStyle.WHEAT)
                .upc("123")
                .price(new BigDecimal("11.99"))
                .build());

        beerRepository.flush();

        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
    }
}