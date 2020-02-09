package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import org.junit.Test;

import static org.junit.Assert.*;

public class VendorMapperTest {
    public static final String NAME = "Joe";
    public static final long ID = 1L;

    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    @Test
    public void customerToCustomerDTO() throws Exception {

        //given
        Vendor vendor = new Vendor();
        vendor.setName(NAME);
        vendor.setId(ID);

        //when
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);

        //then
        assertEquals(NAME, vendorDTO.getName());
    }
}