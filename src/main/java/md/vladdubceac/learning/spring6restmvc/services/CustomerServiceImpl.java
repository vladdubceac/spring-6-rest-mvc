package md.vladdubceac.learning.spring6restmvc.services;

import md.vladdubceac.learning.spring6restmvc.model.CustomerDTO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService{

    private Map<UUID, CustomerDTO> customersMap;

    public CustomerServiceImpl(){
        customersMap = new HashMap<>();

        CustomerDTO customer1 = CustomerDTO.builder().id(UUID.randomUUID())
                .name("Ion")
                .version(1)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now()).build();

        CustomerDTO customer2 = CustomerDTO.builder().id(UUID.randomUUID())
                .name("Ana")
                .version(1)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now()).build();

        CustomerDTO customer3 = CustomerDTO.builder().id(UUID.randomUUID())
                .name("Company")
                .version(1)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now()).build();
        customersMap.put(customer1.getId(), customer1);
        customersMap.put(customer2.getId(), customer2);
        customersMap.put(customer3.getId(), customer3);
    }

    @Override
    public List<CustomerDTO> getCustomers() {
        return new ArrayList<>(customersMap.values());
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
         return Optional.ofNullable(customersMap.get(id));
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customer) {
        CustomerDTO savedCustomer = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .name(customer.getName())
                .version(Optional.ofNullable(customer.getVersion()).orElse(1))
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();
        customersMap.put(savedCustomer.getId(), savedCustomer);
        return savedCustomer;
    }

    @Override
    public Optional<CustomerDTO> updateCustomer(UUID id, CustomerDTO customer) {
        CustomerDTO existing = customersMap.get(id);
        if(existing != null){
            existing.setName(customer.getName());
            if(customer.getVersion()!=null){
                existing.setVersion(customer.getVersion());
            }
            existing.setUpdatedDate(LocalDateTime.now());
        }
//        customersMap.put(id, existing);
        return Optional.ofNullable(existing);
    }

    @Override
    public boolean delete(UUID id) {
        return customersMap.remove(id) != null;
    }

    @Override
    public Optional<CustomerDTO> patchById(UUID id, CustomerDTO customer) {
        CustomerDTO existingCustomer = customersMap.get(id);
        if(existingCustomer == null) {
            return Optional.empty();
        }
        if(customer.getVersion()!=null) {
            existingCustomer.setVersion(customer.getVersion());
        }
        if(StringUtils.hasText(customer.getName())){
            existingCustomer.setName(customer.getName());
        }
        if(customer.getCreatedDate()!=null){
            existingCustomer.setCreatedDate(customer.getCreatedDate());
        }
        existingCustomer.setUpdatedDate(LocalDateTime.now());
        return Optional.of(existingCustomer);
    }
}
