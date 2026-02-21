package com.springlearning.spring_7_rest_mvc.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.springlearning.spring_7_rest_mvc.model.Customer;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService{

	private Map<UUID , Customer> customerMap;
	
	
	public CustomerServiceImpl() {
		customerMap = new HashMap<>();
		
		Customer customer1 = Customer.builder()
				.id(UUID.randomUUID())
				.customerName("CustomerName1")
				.version(1)
				.createdDate(LocalDateTime.now())
				.lastModifieddate(LocalDateTime.now())
				.build();
		Customer customer2 = Customer.builder()
				.id(UUID.randomUUID())
				.customerName("CustomerName2")
				.version(1)
				.createdDate(LocalDateTime.now())
				.lastModifieddate(LocalDateTime.now())
				.build();
		Customer customer3 = Customer.builder()
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
	public List<Customer> listCustomers() {
		return new ArrayList<>(customerMap.values());
	}

	@Override
	public Customer getCustomerById(UUID id) {
		return customerMap.get(id);
	}

	@Override
	public Customer saveCustomer(Customer customer) {
		Customer savedCustomer = Customer.builder()
				.id(UUID.randomUUID())
				.customerName(customer.getCustomerName())
				.version(1)
				.createdDate(LocalDateTime.now())
				.lastModifieddate(LocalDateTime.now())
				.build();
		customerMap.put(savedCustomer.getId(), savedCustomer);
		return savedCustomer;
	}

}
