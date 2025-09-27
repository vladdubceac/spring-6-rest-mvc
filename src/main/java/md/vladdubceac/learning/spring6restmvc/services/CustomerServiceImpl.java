package md.vladdubceac.learning.spring6restmvc.services;

import md.vladdubceac.learning.spring6restmvc.model.Customer;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService{

    private Map<UUID, Customer> customersMap;

    public CustomerServiceImpl(){
        customersMap = new HashMap<>();

        Customer customer1 = Customer.builder().id(UUID.randomUUID())
                .name("Ion")
                .version(1)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now()).build();

        Customer customer2 = Customer.builder().id(UUID.randomUUID())
                .name("Ana")
                .version(1)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now()).build();

        Customer customer3 = Customer.builder().id(UUID.randomUUID())
                .name("Company")
                .version(1)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now()).build();
        customersMap.put(customer1.getId(), customer1);
        customersMap.put(customer2.getId(), customer2);
        customersMap.put(customer3.getId(), customer3);
    }

    @Override
    public List<Customer> getCustomers() {
        return new ArrayList<>(customersMap.values());
    }

    @Override
    public Customer getCustomerById(UUID id) {
        return customersMap.get(id);
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        Customer savedCustomer = Customer.builder()
                .id(UUID.randomUUID())
                .name(customer.getName())
                .version(Optional.ofNullable(customer.getVersion()).orElse(1))
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();
        customersMap.put(savedCustomer.getId(), savedCustomer);
        return savedCustomer;
    }
}
