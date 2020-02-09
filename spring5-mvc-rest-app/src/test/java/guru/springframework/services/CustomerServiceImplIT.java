package guru.springframework.services;

import guru.springframework.api.v1.mapper.CustomerMapper;
import guru.springframework.bootstrap.Bootstrap;
import guru.springframework.domain.Customer;
import guru.springframework.model.CustomerDTO;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.CustomerRepository;
import guru.springframework.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerServiceImplIT {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    VendorRepository vendorRepository;

    CustomerService customerService;

    @Before
    public void setUp() throws Exception {
        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository, vendorRepository);
        bootstrap.run();

        customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
    }

    @Test
    public void patchCustomerUpdateFirstName() {
        String newFirstName = "Test1";
        Long id = getCustomerId();

        Customer originalCustomer = customerRepository.getOne(id);

        String originalFirstName = originalCustomer.getFirstname();
        String originalLastName = originalCustomer.getLastname();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(newFirstName);

        customerService.patchCustomerDTO(id, customerDTO);

        Customer updatedCustomer = customerRepository.findById(id).get();

        assertNotNull(updatedCustomer);
        assertEquals(newFirstName, updatedCustomer.getFirstname());
        assertThat(originalFirstName, not(updatedCustomer.getFirstname()));
        assertEquals(originalLastName, updatedCustomer.getLastname());


    }

    @Test
    public void patchCustomerUpdateLastName() {
        String newLastName = "Test1";
        Long id = getCustomerId();

        Customer originalCustomer = customerRepository.getOne(id);

        String originalFirstName = originalCustomer.getFirstname();
        String originalLastName = originalCustomer.getLastname();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setLastname(newLastName);

        customerService.patchCustomerDTO(id, customerDTO);

        Customer updatedCustomer = customerRepository.findById(id).get();

        assertNotNull(updatedCustomer);
        assertEquals(newLastName, updatedCustomer.getLastname());
        assertThat(originalLastName, not(updatedCustomer.getLastname()));
        assertEquals(originalFirstName, updatedCustomer.getFirstname());

    }

    private Long getCustomerId() {
        List<Customer> customerList = customerRepository.findAll();
        return customerList.get(0).getId();
    }
}