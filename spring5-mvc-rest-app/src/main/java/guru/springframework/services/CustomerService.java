package guru.springframework.services;



import guru.springframework.model.CustomerDTO;

import java.util.List;

public interface CustomerService {
    List<CustomerDTO> getAllCustomers();
    CustomerDTO getCustomerById(Long id);
    CustomerDTO createNewCustomer(CustomerDTO customerDTO);
    CustomerDTO saveCustomerDTO(Long id, CustomerDTO customerDTO);
    CustomerDTO patchCustomerDTO(Long id, CustomerDTO customerDTO);
    void deleteCustomerById(Long id);
}
