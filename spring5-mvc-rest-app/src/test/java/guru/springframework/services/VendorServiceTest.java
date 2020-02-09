package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class VendorServiceTest {
    @Mock
    VendorRepository vendorRepository;

    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    VendorService vendorService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        vendorService = new VendorServiceImp(vendorRepository, vendorMapper);
    }

    @Test
    public void getAllVendors() throws Exception {
        //given
        Vendor vendor1 = new Vendor();
        vendor1.setId(1l);
        vendor1.setName("Michale");

        Vendor vendor2 = new Vendor();
        vendor2.setId(2l);
        vendor2.setName("Sam");

        when(vendorRepository.findAll()).thenReturn(Arrays.asList(vendor1, vendor2));

        //when
        List<VendorDTO> VendorDTOS = vendorService.getAllVendors();

        //then
        assertEquals(2, VendorDTOS.size());

    }

    @Test
    public void getVendorById() throws Exception {
        //given
        Vendor vendor1 = new Vendor();
        vendor1.setId(1l);
        vendor1.setName("Michale");

        when(vendorRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(vendor1));

        //when
        VendorDTO vendorDTO = vendorService.getVendorById(1L);

        assertEquals("Michale", vendorDTO.getName());
    }

    @Test
    public void createNewVendor() throws Exception {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Test");

        Vendor savedVendor = new Vendor();
        savedVendor.setName(vendorDTO.getName());
        savedVendor.setId(1L);

        when(vendorRepository.save(any())).thenReturn(savedVendor);

        VendorDTO savedDto = vendorService.createNewVendor(vendorDTO);

        assertEquals(vendorDTO.getName(), savedDto.getName());
        assertEquals("/api/v1/vendor/1", savedDto.getCustomUrl());
    }

    @Test
    public void saveVendorByDTO() throws Exception {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Test");

        Vendor savedVendor = new Vendor();
        savedVendor.setName(vendorDTO.getName());
        savedVendor.setId(1L);

        when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);

        VendorDTO returenedDTO = vendorService.saveVendorDTO(1L, vendorDTO);

        assertEquals(vendorDTO.getName(), returenedDTO.getName());
        assertEquals("/api/v1/vendor/1", returenedDTO.getCustomUrl());
    }

    @Test
    public void deleteVendorById() throws Exception {

        Long id = 1L;
        vendorRepository.deleteById(id);

        verify(vendorRepository, times(1)).deleteById(anyLong());
    }
}