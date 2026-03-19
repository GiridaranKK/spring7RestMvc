package com.springlearning.spring_7_rest_mvc.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.springlearning.spring_7_rest_mvc.entities.Customer;
import com.springlearning.spring_7_rest_mvc.model.CustomerDTO;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService{

	private Map<UUID , CustomerDTO> customerMap;
	
	
	public CustomerServiceImpl() {
		customerMap = new HashMap<>();
		
		CustomerDTO customer1 = CustomerDTO.builder()
				.id(UUID.randomUUID())
				.customerName("CustomerName1")
				.version(1)
				.createdDate(LocalDateTime.now())
				.lastModifieddate(LocalDateTime.now())
				.build();
		CustomerDTO customer2 = CustomerDTO.builder()
				.id(UUID.randomUUID())
				.customerName("CustomerName2")
				.version(1)
				.createdDate(LocalDateTime.now())
				.lastModifieddate(LocalDateTime.now())
				.build();
		CustomerDTO customer3 = CustomerDTO.builder()
				.id(UUID.randomUUID())
				.customerName("CustomerName3")
				.version(1)
				.createdDate(LocalDateTime.now())
				.lastModifieddate(LocalDateTime.now())
				.build();
		customerMap.put(customer1.getId(), customer1);
		customerMap.put(customer2.getId(), customer2);
		customerMap.put(customer3.getId(), customer3);
	}

	@Override
	public List<CustomerDTO> listCustomers() {
		return new ArrayList<>(customerMap.values());
	}

	@Override
	public Optional<CustomerDTO> getCustomerById(UUID id) {
		return Optional.of(customerMap.get(id));
	}

	@Override
	public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
		CustomerDTO savedCustomer = CustomerDTO.builder()
				.id(UUID.randomUUID())
				.customerName(customerDTO.getCustomerName())
				.version(1)
				.createdDate(LocalDateTime.now())
				.lastModifieddate(LocalDateTime.now())
				.build();
		customerMap.put(savedCustomer.getId(), savedCustomer);
		return savedCustomer;
	}

	@Override
	public Optional<CustomerDTO> updateCustomer(UUID customerId, CustomerDTO customerDTO) {
		CustomerDTO existingCustomer = customerMap.get(customerId);
		existingCustomer.setCustomerName(customerDTO.getCustomerName());  
		customerMap.put(existingCustomer.getId(), existingCustomer);
		return Optional.of(existingCustomer);
	}

	@Override
	public Boolean deleteCustomerById(UUID customerId) {
		customerMap.remove(customerId);
		return true;
	}

	@Override
	public Optional<CustomerDTO> patchCustomerById(UUID customerId, CustomerDTO customerDTO) {
		CustomerDTO existingCustomer = customerMap.get(customerId);
		if(StringUtils.hasText(customerDTO.getCustomerName())) {
			existingCustomer.setCustomerName(customerDTO.getCustomerName());
		}
		return Optional.of(existingCustomer);
	}

}
