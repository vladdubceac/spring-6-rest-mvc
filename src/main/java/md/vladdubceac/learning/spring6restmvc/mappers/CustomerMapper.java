package md.vladdubceac.learning.spring6restmvc.mappers;

import md.vladdubceac.learning.spring6restmvc.entities.Customer;
import md.vladdubceac.learning.spring6restmvc.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    Customer customerDtoToCustomer(CustomerDTO dto);

    CustomerDTO customerToCustomerDTO(Customer customer);
}
