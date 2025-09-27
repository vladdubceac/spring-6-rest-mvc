package md.vladdubceac.learning.spring6restmvc.services;

import md.vladdubceac.learning.spring6restmvc.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    List<Customer> getCustomers();
    Customer getCustomerById(UUID id);

    Customer saveCustomer(Customer customer);

    Customer updateCustomer(UUID id, Customer customer);
}
