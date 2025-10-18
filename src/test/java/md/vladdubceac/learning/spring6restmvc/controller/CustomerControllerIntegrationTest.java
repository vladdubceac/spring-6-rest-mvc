package md.vladdubceac.learning.spring6restmvc.controller;

import md.vladdubceac.learning.spring6restmvc.entities.Customer;
import md.vladdubceac.learning.spring6restmvc.mappers.CustomerMapper;
import md.vladdubceac.learning.spring6restmvc.model.CustomerDTO;
import md.vladdubceac.learning.spring6restmvc.repositories.CustomerRepository;
import md.vladdubceac.learning.spring6restmvc.utils.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerControllerIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerController customerController;
    @Autowired
    private CustomerMapper customerMapper;

    @Test
    @Transactional
    @Rollback
    void testDeleteByIdFound() {
        Customer customer = customerRepository.findAll().getFirst();

        ResponseEntity responseEntity = customerController.deleteCustomer(customer.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        assertThat(customerRepository.findById(customer.getId()).isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    void testUpdateCustomer() {
        Customer customer = customerRepository.findAll().getFirst();
        CustomerDTO dto = customerMapper.customerToCustomerDTO(customer);
        final String newName = "Updated customer";
        dto.setName(newName);
        dto.setId(null);
        dto.setVersion(null);

        ResponseEntity responseEntity = customerController.updateCustomer(customer.getId(), dto);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Customer updatedCustomer = customerRepository.findById(customer.getId()).get();
        assertThat(updatedCustomer.getName()).isEqualTo(newName);
    }

    @Test
    void testUpdateByIdNotFound() {
        assertThrows(NotFoundException.class, () -> customerController.updateCustomer(UUID.randomUUID(), CustomerDTO.builder().build()));
    }

    @Test
    @Transactional
    @Rollback
    void testSaveCustomer() {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .name("Test new customer")
                .build();

        ResponseEntity responseEntity = customerController.createCustomer(customerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);

        Customer savedCustomer = customerRepository.findById(savedUUID).get();
        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getName()).isEqualTo(customerDTO.getName());
    }

    @Test
    void testDeleteByIdNotFound() {
        assertThrows(NotFoundException.class, () -> customerController.deleteCustomer(UUID.randomUUID()));
    }

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
    void testGetCustomerByIdNotFound() {
        Set<UUID> uuidSet = customerRepository.findAll().stream().map(Customer::getId).collect(Collectors.toSet());
        UUID uuid = UUID.randomUUID();
        while (uuidSet.contains(uuid)) {
            uuid = UUID.randomUUID();
        }
        final UUID finalCopy = uuid;
        assertThrows(NotFoundException.class, () -> customerController.getCustomerById(finalCopy));
    }

    @Test
    @Transactional
    @Rollback
    void patchById() {
        Customer customer = customerRepository.findAll().getFirst();
        UUID customerId = customer.getId();
        CustomerDTO dto = CustomerDTO.builder().name("Patched Customer").build();
        ResponseEntity responseEntity = customerController.patchById(customerId, dto);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Customer updated = customerRepository.findById(customerId).orElse(null);
        assertThat(updated).isNotNull();
        assertThat(updated.getName()).isEqualTo("Patched Customer");
    }

    @Test
    void getCustomers() {
        List<CustomerDTO> customerDTOs = customerController.getCustomers();
        assertThat(customerDTOs).isNotEmpty();
        assertThat(customerDTOs.size()).isEqualTo(3);
    }

    @Test
    void getCustomerById() {
        List<Customer> customers = customerRepository.findAll();
        assertFalse(customers.isEmpty());
        UUID customerId = customers.getFirst().getId();
        assertNotNull(customerId);
        CustomerDTO dto = customerController.getCustomerById(customerId);
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(customerId);
    }
}