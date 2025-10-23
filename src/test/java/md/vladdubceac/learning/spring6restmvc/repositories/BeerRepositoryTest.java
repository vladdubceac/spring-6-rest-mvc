package md.vladdubceac.learning.spring6restmvc.repositories;

import jakarta.validation.ConstraintViolationException;
import md.vladdubceac.learning.spring6restmvc.entities.Beer;
import md.vladdubceac.learning.spring6restmvc.model.BeerStyle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class BeerRepositoryTest {
    @Autowired
    BeerRepository beerRepository;

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