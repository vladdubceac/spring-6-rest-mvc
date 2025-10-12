package md.vladdubceac.learning.spring6restmvc.services;

import lombok.RequiredArgsConstructor;
import md.vladdubceac.learning.spring6restmvc.mappers.CustomerMapper;
import md.vladdubceac.learning.spring6restmvc.model.CustomerDTO;
import md.vladdubceac.learning.spring6restmvc.repositories.CustomerRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Primary
@RequiredArgsConstructor
public class CustomerServiceJPA implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerDTO> getCustomers() {
        return List.of();
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        return Optional.empty();
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customer) {
        return null;
    }

    @Override
    public CustomerDTO updateCustomer(UUID id, CustomerDTO customer) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }

    @Override
    public void patchById(UUID id, CustomerDTO customer) {

    }
}
