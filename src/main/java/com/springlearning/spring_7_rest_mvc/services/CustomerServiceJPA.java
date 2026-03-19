package com.springlearning.spring_7_rest_mvc.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.springlearning.spring_7_rest_mvc.mappers.BeerMapper;
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
		return customerRepository.findAll()
				.stream()
				.map(customerMapper :: customerToCustomerDto)
				.collect(Collectors.toList());
	}

	@Override
	public Optional<CustomerDTO> getCustomerById(UUID id) {
		// TODO Auto-generated method stub
		return Optional.ofNullable(customerMapper.customerToCustomerDto(customerRepository.findById(id).orElse(null)));
	}

	@Override
	public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
		// TODO Auto-generated method stub
		return customerMapper.customerToCustomerDto(customerRepository.save(customerMapper.customerDtoToCustomer(customerDTO)));
	}

	@Override
	public Optional<CustomerDTO> updateCustomer(UUID customerId, CustomerDTO customerDTO) {
		
		AtomicReference<Optional<CustomerDTO>> atomicReference = new AtomicReference<>();
		customerRepository.findById(customerId).ifPresentOrElse(foundCustomer -> {
			foundCustomer.setCustomerName(customerDTO.getCustomerName());
			atomicReference.set(Optional.of(customerMapper.customerToCustomerDto(customerRepository.save(foundCustomer))));
		}, () ->{
			atomicReference.set(Optional.empty());
		});
		return atomicReference.get();
		
	}

	@Override
	public Boolean deleteCustomerById(UUID customerId) {
		if(customerRepository.existsById(customerId)) {
			customerRepository.deleteById(customerId);
			return true;
		}
		return false;
	}

	@Override
	public Optional<CustomerDTO> patchCustomerById(UUID customerId, CustomerDTO customerDTO) {
		
		AtomicReference<Optional<CustomerDTO>> atomicReference = new AtomicReference<>();
		customerRepository.findById(customerId).ifPresentOrElse(foundCustomer -> {
			if(StringUtils.hasText(customerDTO.getCustomerName())) {
				foundCustomer.setCustomerName(customerDTO.getCustomerName());
			}
			atomicReference.set(Optional.of(customerMapper.customerToCustomerDto(customerRepository.save(foundCustomer))));
		}, () ->{
			atomicReference.set(Optional.empty());
		});
		return atomicReference.get();
	}

}
