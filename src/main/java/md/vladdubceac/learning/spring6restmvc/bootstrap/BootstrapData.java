package md.vladdubceac.learning.spring6restmvc.bootstrap;

import lombok.RequiredArgsConstructor;
import md.vladdubceac.learning.spring6restmvc.entities.Beer;
import md.vladdubceac.learning.spring6restmvc.entities.Customer;
import md.vladdubceac.learning.spring6restmvc.model.BeerStyle;
import md.vladdubceac.learning.spring6restmvc.repositories.BeerRepository;
import md.vladdubceac.learning.spring6restmvc.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {
    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        insertBeerData();
        insertCustomerData();
    }

    private void insertBeerData() {
        if (beerRepository.count() == 0) {
            Beer beer1 = Beer.builder()
                    .beerName("Chisinau")
                    .beerStyle(BeerStyle.WHEAT)
                    .upc("123456")
                    .price(new BigDecimal("2.99"))
                    .quantityOnHand(123)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();
            Beer beer2 = Beer.builder()
                    .beerName("Vitanta")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .upc("123456")
                    .price(new BigDecimal("2.49"))
                    .quantityOnHand(234)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();
            Beer beer3 = Beer.builder()
                    .beerName("Orasul meu")
                    .beerStyle(BeerStyle.WHEAT)
                    .upc("2345")
                    .price(new BigDecimal("2.09"))
                    .quantityOnHand(345)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            beerRepository.save(beer1);
            beerRepository.save(beer2);
            beerRepository.save(beer3);
        }
    }

    private void insertCustomerData() {
        if (customerRepository.count() == 0) {
            Customer customer1 = Customer.builder()
                    .name("Ion")
                    .version(1)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();
            Customer customer2 = Customer.builder()
                    .name("Ana")
                    .version(1)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();
            Customer customer3 = Customer.builder()
                    .name("Company")
                    .version(1)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            customerRepository.save(customer1);
            customerRepository.save(customer2);
            customerRepository.save(customer3);
        }
    }
}
