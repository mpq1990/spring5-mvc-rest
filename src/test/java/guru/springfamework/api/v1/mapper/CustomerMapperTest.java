package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerMapperTest {
    public static final String NAME = "Joe";
    public static final long ID = 1L;

    CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Test
    public void customerToCustomerDTO() throws Exception {

        //given
        Customer customer = new Customer();
        customer.setFirstName(NAME);
        customer.setId(ID);

        //when
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        //then
        assertEquals(NAME, customerDTO.getFirstName());
    }
}