package com.springlearning.spring_7_rest_mvc.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.springlearning.spring_7_rest_mvc.entities.Customer;
import com.springlearning.spring_7_rest_mvc.model.CustomerDTO;

public interface CustomerService {
	List<CustomerDTO> listCustomers();
	Optional<CustomerDTO> getCustomerById(UUID id);
	CustomerDTO saveCustomer(CustomerDTO customerDTO);
	Optional<CustomerDTO> updateCustomer(UUID customerId, CustomerDTO customerDTO);
	Boolean deleteCustomerById(UUID customerId);
	void patchCustomerById(UUID customerId, CustomerDTO customerDTO);
}
