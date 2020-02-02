package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.controllers.RestResponseEntityExceptionHandler;
import guru.springfamework.services.VendorService;
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

public class VendorControllerTest {
    @Mock
    VendorService vendorService;

    @InjectMocks
    VendorController vendorController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(vendorController).
                setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
    }

    @Test
    public void testListCustomers() throws Exception {

        //given
        VendorDTO vendor1 = new VendorDTO();
        vendor1.setName("Michale");
        vendor1.setCustomUrl("/api/v1/vendor/1");

        VendorDTO vendor2 = new VendorDTO();
        vendor2.setName("Sam");
        vendor2.setCustomUrl("/api/v1/vendor/2");

        when(vendorService.getAllVendors()).thenReturn(Arrays.asList(vendor1, vendor2));

        mockMvc.perform(get("/api/v1/vendors/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));
    }

    @Test
    public void testGetCustomerById() throws Exception {

        //given
        VendorDTO vendor1 = new VendorDTO();
        vendor1.setName("Michale");
        vendor1.setCustomUrl("/api/v1/vendor/1");

        when(vendorService.getVendorById(anyLong())).thenReturn(vendor1);

        //when
        mockMvc.perform(get("/api/v1/vendors/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Michale")));
    }

    @Test
    public void createNewCustomer() throws Exception {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Test");

        VendorDTO returnedDto = new VendorDTO();
        returnedDto.setName(vendorDTO.getName());
        returnedDto.setCustomUrl("/api/v1/vendors/1");

        when(vendorService.createNewVendor(vendorDTO)).thenReturn(returnedDto);

        mockMvc.perform(post("/api/v1/vendors/").
                contentType(MediaType.APPLICATION_JSON).
                content(asJsonString(vendorDTO))).
                andExpect(status().isCreated()).
                andExpect(jsonPath("$.name", equalTo("Test"))).
                andExpect(jsonPath("$.custom_url", equalTo("/api/v1/vendors/1")));

    }

    @Test
    public void testUpdateCustomer() throws Exception {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Test");

        VendorDTO returnedDto = new VendorDTO();
        returnedDto.setName(vendorDTO.getName());
        returnedDto.setCustomUrl("/api/v1/vendors/1");

        when(vendorService.saveVendorDTO(anyLong(), any(VendorDTO.class))).thenReturn(returnedDto);

        mockMvc.perform(put("/api/v1/vendors/1").
                contentType(MediaType.APPLICATION_JSON).
                content(asJsonString(vendorDTO))).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.name", equalTo("Test"))).
                andExpect(jsonPath("$.custom_url", equalTo("/api/v1/vendors/1")));

    }

    @Test
    public void testPatchCustomer() throws Exception {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Test");

        VendorDTO returnedDto = new VendorDTO();
        returnedDto.setName(vendorDTO.getName());
        returnedDto.setCustomUrl("/api/v1/vendors/1");

        when(vendorService.patchVendorDTO(anyLong(), any(VendorDTO.class))).thenReturn(returnedDto);

        mockMvc.perform(patch("/api/v1/vendors/1").
                contentType(MediaType.APPLICATION_JSON).
                content(asJsonString(vendorDTO))).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.name", equalTo("Test"))).
                andExpect(jsonPath("$.custom_url", equalTo("/api/v1/vendors/1")));

    }

    @Test
    public void testDeleteCustomer() throws Exception {

        mockMvc.perform(delete("/api/v1/vendors/1").
                contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk());
        verify(vendorService, times(1)).deleteVendorById(anyLong());
    }
}