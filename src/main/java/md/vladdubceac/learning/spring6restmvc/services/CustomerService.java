package md.vladdubceac.learning.spring6restmvc.services;

import md.vladdubceac.learning.spring6restmvc.model.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    List<CustomerDTO> getCustomers();

    Optional<CustomerDTO> getCustomerById(UUID id);

    CustomerDTO saveCustomer(CustomerDTO customer);

    CustomerDTO updateCustomer(UUID id, CustomerDTO customer);

    void delete(UUID id);

    void patchById(UUID id, CustomerDTO customer);
}
