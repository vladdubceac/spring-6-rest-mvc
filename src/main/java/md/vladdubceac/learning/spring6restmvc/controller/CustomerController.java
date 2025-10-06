package md.vladdubceac.learning.spring6restmvc.controller;

import lombok.RequiredArgsConstructor;
import md.vladdubceac.learning.spring6restmvc.model.Customer;
import md.vladdubceac.learning.spring6restmvc.services.CustomerService;
import md.vladdubceac.learning.spring6restmvc.utils.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    public static final String ID_VARIABLE = "id";
    public static final String PATH = "/api/v1/customer/";
    public static final String PATH_ID = PATH + "{" + ID_VARIABLE + "}";

    @PatchMapping(PATH_ID)
    public ResponseEntity patchById(@PathVariable(ID_VARIABLE)UUID id, @RequestBody Customer customer) {
        customerService.patchById(id, customer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(PATH_ID)
    public ResponseEntity deleteCustomer(@PathVariable(ID_VARIABLE) UUID id) {
        customerService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(PATH_ID)
    public ResponseEntity updateCustomer(@PathVariable(ID_VARIABLE) UUID id, @RequestBody Customer customer) {
        customerService.updateCustomer(id, customer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping(PATH)
    public ResponseEntity createCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.saveCustomer(customer);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location", PATH+ savedCustomer.getId().toString());
        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);
    }

    @GetMapping(PATH)
    public List<Customer> getCustomers() {
        return customerService.getCustomers();
    }

    @GetMapping(PATH_ID)
    public Customer getCustomerById(@PathVariable(ID_VARIABLE) UUID customerId) {
        return customerService.getCustomerById(customerId).orElseThrow(NotFoundException::new);
    }
}
