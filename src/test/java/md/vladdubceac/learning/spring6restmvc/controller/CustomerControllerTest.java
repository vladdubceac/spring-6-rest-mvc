package md.vladdubceac.learning.spring6restmvc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import md.vladdubceac.learning.spring6restmvc.model.Beer;
import md.vladdubceac.learning.spring6restmvc.model.Customer;
import md.vladdubceac.learning.spring6restmvc.services.CustomerService;
import md.vladdubceac.learning.spring6restmvc.services.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    CustomerService customerService;

    @Autowired
    ObjectMapper objectMapper;

    private CustomerServiceImpl customerServiceImpl;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<Customer> customerArgumentCaptor;

    @BeforeEach
    void setUp(){
        customerServiceImpl = new CustomerServiceImpl();
    }

    @Test
    void testGetCustomers() throws Exception {
        given(customerService.getCustomers()).willReturn(customerServiceImpl.getCustomers());

        mockMvc.perform(get("/api/v1/customer")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    void testGetCustomerById() throws Exception {
        Customer testCustomer = customerServiceImpl.getCustomers().getFirst();
        UUID id = testCustomer.getId();
        given(customerService.getCustomerById(id)).willReturn(testCustomer);

        mockMvc.perform(get("/api/v1/customer/"+ id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(id.toString())))
                .andExpect(jsonPath("$.name", is(testCustomer.getName())))
                .andExpect(jsonPath("$.version", is(testCustomer.getVersion())));
    }

    @Test
    void testCreateNewCustomer() throws Exception {
        Customer customer = Customer.builder()
                        .name("New customer")
                                .version(1)
                                        .build();
        given(customerService.saveCustomer(any(Customer.class))).willReturn(customerServiceImpl.getCustomers().getFirst());

        mockMvc.perform(post("/api/v1/customer")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testUpdateById() throws Exception {
        Customer customer = customerServiceImpl.getCustomers().getFirst();
        UUID id = customer.getId();

        mockMvc.perform(put("/api/v1/customer/"+id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isNoContent());

        verify(customerService).updateCustomer(id, customer);
    }

    @Test
    void testDeleteCustomer() throws Exception {
        Customer customer = customerServiceImpl.getCustomers().getFirst();
        UUID id = customer.getId();

        mockMvc.perform(delete("/api/v1/customer/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(customerService).delete(uuidArgumentCaptor.capture());
        assertThat(id).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void testPatchById() throws Exception {
        Customer customer = customerServiceImpl.getCustomers().getFirst();
        UUID id = customer.getId();

        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("name", "Updated customer");

        mockMvc.perform(patch("/api/v1/customer/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateMap)))
                .andExpect(status().isNoContent());

        verify(customerService).patchById(uuidArgumentCaptor.capture(), customerArgumentCaptor.capture());

        assertThat(customer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        assertThat(updateMap.get("name")).isEqualTo(customerArgumentCaptor.getValue().getName());
    }
}
