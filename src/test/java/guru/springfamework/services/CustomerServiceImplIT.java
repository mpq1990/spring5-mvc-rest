package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.bootstrap.Bootstrap;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
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

    CustomerService customerService;

    @Before
    public void setUp() throws Exception {
        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository);
        bootstrap.run();

        customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
    }

    @Test
    public void patchCustomerUpdateFirstName() {
        String newFirstName = "Test1";
        Long id = getCustomerId();

        Customer originalCustomer = customerRepository.getOne(id);

        String originalFirstName = originalCustomer.getFirstName();
        String originalLastName = originalCustomer.getLastName();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(newFirstName);

        customerService.patchCustomerDTO(id, customerDTO);

        Customer updatedCustomer = customerRepository.findById(id).get();

        assertNotNull(updatedCustomer);
        assertEquals(newFirstName, updatedCustomer.getFirstName());
        assertThat(originalFirstName, not(updatedCustomer.getFirstName()));
        assertEquals(originalLastName, updatedCustomer.getLastName());


    }

    @Test
    public void patchCustomerUpdateLastName() {
        String newLastName = "Test1";
        Long id = getCustomerId();

        Customer originalCustomer = customerRepository.getOne(id);

        String originalFirstName = originalCustomer.getFirstName();
        String originalLastName = originalCustomer.getLastName();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setLastName(newLastName);

        customerService.patchCustomerDTO(id, customerDTO);

        Customer updatedCustomer = customerRepository.findById(id).get();

        assertNotNull(updatedCustomer);
        assertEquals(newLastName, updatedCustomer.getLastName());
        assertThat(originalLastName, not(updatedCustomer.getLastName()));
        assertEquals(originalFirstName, updatedCustomer.getFirstName());

    }

    private Long getCustomerId() {
        List<Customer> customerList = customerRepository.findAll();
        return customerList.get(0).getId();
    }
}