package md.vladdubceac.learning.spring6restmvc.services;

import lombok.RequiredArgsConstructor;
import md.vladdubceac.learning.spring6restmvc.entities.Customer;
import md.vladdubceac.learning.spring6restmvc.mappers.CustomerMapper;
import md.vladdubceac.learning.spring6restmvc.model.CustomerDTO;
import md.vladdubceac.learning.spring6restmvc.repositories.CustomerRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public CustomerDTO saveCustomer(CustomerDTO dto) {
        Customer customer = customerMapper.customerDtoToCustomer(dto);
        customer = customerRepository.save(customer);
        return customerMapper.customerToCustomerDTO(customer);
    }

    @Override
    public Optional<CustomerDTO> updateCustomer(UUID id, CustomerDTO customerDTO) {
        Optional<Customer> resultOptional = customerRepository.findById(id);
        if (resultOptional.isPresent()) {
            Customer customer = resultOptional.get();
            if (customerDTO.getName() != null) {
                customer.setName(customerDTO.getName());
            }
            if (customerDTO.getVersion() != null) {
                customer.setVersion(customerDTO.getVersion());
            }
            if(customerDTO.getCreatedDate()!=null) {
                customer.setCreatedDate(customerDTO.getCreatedDate());
            }
            customer.setUpdatedDate(LocalDateTime.now());
            customer = customerRepository.save(customer);
            return Optional.ofNullable(customerMapper.customerToCustomerDTO(customer));
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(UUID id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<CustomerDTO> patchById(UUID id, CustomerDTO customer) {
        if (customerRepository.existsById(id)) {
            Customer existing = customerRepository.findById(id).get();
            if (customer.getName() != null) {
                existing.setName(customer.getName());
            }
            if (customer.getVersion() != null) {
                existing.setVersion(customer.getVersion());
            }
            if (customer.getCreatedDate() != null) {
                existing.setCreatedDate(customer.getCreatedDate());
            }
            existing.setUpdatedDate(Optional.ofNullable(customer.getUpdatedDate()).orElse(LocalDateTime.now()));
            return Optional.of(customerMapper.customerToCustomerDTO(customerRepository.save(existing)));
        }
        return Optional.empty();
    }
}
