package md.vladdubceac.learning.spring6restmvc.services;

import md.vladdubceac.learning.spring6restmvc.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    List<Customer> getCustomers();
    Optional<Customer> getCustomerById(UUID id);

    Customer saveCustomer(Customer customer);

    Customer updateCustomer(UUID id, Customer customer);

    void delete(UUID id);

    void patchById(UUID id, Customer customer);
}
