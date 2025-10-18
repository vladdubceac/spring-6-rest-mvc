package md.vladdubceac.learning.spring6restmvc.controller;

import lombok.RequiredArgsConstructor;
import md.vladdubceac.learning.spring6restmvc.model.CustomerDTO;
import md.vladdubceac.learning.spring6restmvc.services.CustomerService;
import md.vladdubceac.learning.spring6restmvc.utils.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CustomerController {
    public static final String ID_VARIABLE = "id";
    public static final String PATH = "/api/v1/customer/";
    public static final String PATH_ID = PATH + "{" + ID_VARIABLE + "}";
    private final CustomerService customerService;

    @PatchMapping(PATH_ID)
    public ResponseEntity patchById(@PathVariable(ID_VARIABLE) UUID id, @RequestBody CustomerDTO customer) {
        customerService.patchById(id, customer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(PATH_ID)
    public ResponseEntity deleteCustomer(@PathVariable(ID_VARIABLE) UUID id) {
        boolean isDeleted = customerService.delete(id);
        if (!isDeleted) {
            throw new NotFoundException();
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(PATH_ID)
    public ResponseEntity updateCustomer(@PathVariable(ID_VARIABLE) UUID id, @RequestBody CustomerDTO customer) {
        Optional<CustomerDTO> resultOptional = customerService.updateCustomer(id, customer);
        if (resultOptional.isEmpty()) {
            throw new NotFoundException();
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping(PATH)
    public ResponseEntity createCustomer(@RequestBody CustomerDTO customer) {
        CustomerDTO savedCustomer = customerService.saveCustomer(customer);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location", PATH + savedCustomer.getId().toString());
        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);
    }

    @GetMapping(PATH)
    public List<CustomerDTO> getCustomers() {
        return customerService.getCustomers();
    }

    @GetMapping(PATH_ID)
    public CustomerDTO getCustomerById(@PathVariable(ID_VARIABLE) UUID customerId) {
        return customerService.getCustomerById(customerId).orElseThrow(NotFoundException::new);
    }
}
