package com.springlearning.spring_7_rest_mvc.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.springlearning.spring_7_rest_mvc.mappers.CustomerMapper;
import com.springlearning.spring_7_rest_mvc.model.CustomerDTO;
import com.springlearning.spring_7_rest_mvc.repositories.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@Primary
@RequiredArgsConstructor
public class CustomerServiceJPA implements CustomerService{
	
	private final CustomerRepository customerRepository;
	private final CustomerMapper customerMapper;

	@Override
	public List<CustomerDTO> listCustomers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<CustomerDTO> getCustomerById(UUID id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateCustomer(UUID customerId, CustomerDTO customerDTO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteCustomerById(UUID customerId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void patchCustomerById(UUID customerId, CustomerDTO customerDTO) {
		// TODO Auto-generated method stub
		
	}

}
