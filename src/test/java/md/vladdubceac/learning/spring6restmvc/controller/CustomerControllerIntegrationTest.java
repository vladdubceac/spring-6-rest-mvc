package md.vladdubceac.learning.spring6restmvc.controller;

import md.vladdubceac.learning.spring6restmvc.entities.Customer;
import md.vladdubceac.learning.spring6restmvc.model.CustomerDTO;
import md.vladdubceac.learning.spring6restmvc.repositories.CustomerRepository;
import md.vladdubceac.learning.spring6restmvc.utils.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CustomerControllerIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerController customerController;

    @Test
    void testListCustomers() {
        List<CustomerDTO> dtoList = customerController.getCustomers();
        assertThat(dtoList).isNotNull();
        assertThat(dtoList.size()).isEqualTo(3);
    }

    @Transactional
    @Rollback
    @Test
    void testEmptyList() {
        customerRepository.deleteAll();

        List<CustomerDTO> customerDTOs = customerController.getCustomers();
        assertThat(customerDTOs).isNotNull();
        assertThat(customerDTOs.size()).isEqualTo(0);
    }

    @Test
    void testGetCustomerById() {
        Customer customer = customerRepository.findAll().getFirst();
        CustomerDTO dto = customerController.getCustomerById(customer.getId());
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(customer.getId());
    }

    @Test
    void testNotFoundException() {
        Set<UUID> uuidSet = customerRepository.findAll().stream().map(Customer::getId).collect(Collectors.toSet());
        UUID uuid = UUID.randomUUID();
        while (uuidSet.contains(uuid)) {
            uuid = UUID.randomUUID();
        }
        final UUID finalCopy = uuid;
        assertThrows(NotFoundException.class, () -> customerController.getCustomerById(finalCopy));
    }
}