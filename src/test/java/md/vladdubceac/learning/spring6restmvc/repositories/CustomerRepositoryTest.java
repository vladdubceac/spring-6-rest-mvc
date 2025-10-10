package md.vladdubceac.learning.spring6restmvc.repositories;

import md.vladdubceac.learning.spring6restmvc.entities.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void testSaveCustomer() {
        String customerName = "My test customer";
        Customer customer = customerRepository.save(Customer.builder().name(customerName).build());

        assertThat(customer.getId()).isNotNull();
        assertThat(customer.getName()).isNotNull();
        assertThat(customer.getName()).isEqualTo(customerName);
    }
}