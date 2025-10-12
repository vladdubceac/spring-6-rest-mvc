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
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class CustomerServiceJPA implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerDTO> getCustomers() {
        return customerRepository.findAll().stream().map(customerMapper::customerToCustomerDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        return Optional.ofNullable(customerMapper.customerToCustomerDTO(customerRepository.findById(id).orElse(null)));
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
