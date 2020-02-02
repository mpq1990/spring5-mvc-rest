package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    CustomerRepository customerRepository;
    CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository,
                               CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream().
                map(customer -> {
                    CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
                    customerDTO.setCustomUrl("/api/v1/customer/" + customer.getId());
                    return customerDTO;
                }).collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        return customerRepository.findById(id).map(customerMapper::customerToCustomerDTO).
                orElseThrow(RuntimeException::new);
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDTOtoCustomer(customerDTO);
        return saveCustomerAndReturnCustomerDTO(customer);
    }

    @Override
    public CustomerDTO saveCustomerDTO(Long id, CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDTOtoCustomer(customerDTO);
        customer.setId(id);

        return saveCustomerAndReturnCustomerDTO(customer);
    }



    private CustomerDTO saveCustomerAndReturnCustomerDTO(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO savedCustomerDTO = customerMapper.customerToCustomerDTO(savedCustomer);
        savedCustomerDTO.setCustomUrl("/api/v1/customer/" + savedCustomer.getId());
        return savedCustomerDTO;
    }
}
