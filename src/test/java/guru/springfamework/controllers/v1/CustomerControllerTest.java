package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static guru.springfamework.controllers.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest {

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void testListCustomers() throws Exception {

        //given
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setFirstName("Michale");
        customer1.setLastName("Weston");
        customer1.setCustomUrl("/api/v1/customer/1");

        CustomerDTO customer2 = new CustomerDTO();
        customer2.setFirstName("Sam");
        customer2.setLastName("Axe");
        customer2.setCustomUrl("/api/v1/customer/2");

        when(customerService.getAllCustomers()).thenReturn(Arrays.asList(customer1, customer2));

        mockMvc.perform(get("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    public void testGetCustomerById() throws Exception {

        //given
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setFirstName("Michale");
        customer1.setLastName("Weston");
        customer1.setCustomUrl("/api/v1/customer/1");

        when(customerService.getCustomerById(anyLong())).thenReturn(customer1);

        //when
        mockMvc.perform(get("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("Michale")));
    }

    @Test
    public void createNewCustomer() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Test");
        customerDTO.setLastName("Test");

        CustomerDTO returnedDto = new CustomerDTO();
        returnedDto.setFirstName(customerDTO.getFirstName());
        returnedDto.setLastName(customerDTO.getLastName());
        returnedDto.setCustomUrl("/api/v1/customers/1");

        when(customerService.createNewCustomer(customerDTO)).thenReturn(returnedDto);

        mockMvc.perform(post("/api/v1/customers/").
                contentType(MediaType.APPLICATION_JSON).
                content(asJsonString(customerDTO))).
                andExpect(status().isCreated()).
                andExpect(jsonPath("$.firstName", equalTo("Test"))).
                andExpect(jsonPath("$.lastName", equalTo("Test"))).
                andExpect(jsonPath("$.custom_url", equalTo("/api/v1/customers/1")));

    }

    @Test
    public void testUpdateCustomer() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Test");
        customerDTO.setLastName("Test");

        CustomerDTO returnedDto = new CustomerDTO();
        returnedDto.setFirstName(customerDTO.getFirstName());
        returnedDto.setLastName(customerDTO.getLastName());
        returnedDto.setCustomUrl("/api/v1/customers/1");

        when(customerService.saveCustomerDTO(anyLong(), any(CustomerDTO.class))).thenReturn(returnedDto);

        mockMvc.perform(put("/api/v1/customers/1").
                contentType(MediaType.APPLICATION_JSON).
                content(asJsonString(customerDTO))).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.firstName", equalTo("Test"))).
                andExpect(jsonPath("$.lastName", equalTo("Test"))).
                andExpect(jsonPath("$.custom_url", equalTo("/api/v1/customers/1")));

    }

    @Test
    public void testPatchCustomer() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Test");
        customerDTO.setLastName("Test");

        CustomerDTO returnedDto = new CustomerDTO();
        returnedDto.setFirstName(customerDTO.getFirstName());
        returnedDto.setLastName("newName");
        returnedDto.setCustomUrl("/api/v1/customers/1");

        when(customerService.patchCustomerDTO(anyLong(), any(CustomerDTO.class))).thenReturn(returnedDto);

        mockMvc.perform(patch("/api/v1/customers/1").
                contentType(MediaType.APPLICATION_JSON).
                content(asJsonString(customerDTO))).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.firstName", equalTo("Test"))).
                andExpect(jsonPath("$.lastName", equalTo("newName"))).
                andExpect(jsonPath("$.custom_url", equalTo("/api/v1/customers/1")));

    }

    @Test
    public void testDeleteCustomer() throws Exception {

        mockMvc.perform(delete("/api/v1/customers/1").
                contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk());
        verify(customerService, times(1)).deleteCustomerById(anyLong());
    }
}