package com.springlearning.spring_7_rest_mvc.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import com.springlearning.spring_7_rest_mvc.entities.Customer;
import com.springlearning.spring_7_rest_mvc.mappers.CustomerMapper;
import com.springlearning.spring_7_rest_mvc.model.BeerDTO;
import com.springlearning.spring_7_rest_mvc.model.CustomerDTO;
import com.springlearning.spring_7_rest_mvc.repositories.CustomerRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
public class CustomerControllerIT {

	@Autowired
	CustomerController controller;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	CustomerMapper customerMapper;
	
	@Test
	void testListCustomers() {
		List<CustomerDTO> dtos = controller.getAllCustomers();
		
		assertThat(dtos.size()).isEqualTo(3);
	}
	
	@Rollback
	@Transactional
	@Test
	void testListCustomersNull() {
		customerRepository.deleteAll();
		List<CustomerDTO> dtos = controller.getAllCustomers();
		
		assertThat(dtos.size()).isEqualTo(0);
	}
	
	@Test
	void testCustomerById() {
		Customer customer = customerRepository.findAll().get(0);
		
		CustomerDTO customerDTO = controller.getCustomerByID(customer.getId());
		
		assertThat(customerDTO).isNotNull();
	}
	
	@Test
	void testCustomerByIdNull() {
		
		assertThrows(NotFoundException.class, () -> {
			CustomerDTO customerDTO = controller.getCustomerByID(UUID.randomUUID());
		});
	}
	
	@Rollback
	@Transactional
	@Test
	void testSaveNewCustomer() {
		CustomerDTO customerDTO = CustomerDTO.builder()
				.customerName("customerName4")
				.build();
		
		ResponseEntity responseEntity = controller.saveCustomer(customerDTO);
		
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
		assertThat(responseEntity.getHeaders().getLocation()).isNotNull();
		
		String[] LocationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
		
		UUID uuid = UUID.fromString(LocationUUID[4]);
		
		Customer customer = customerRepository.findById(uuid).get();
		assertThat(customer).isNotNull();
	}
	
	@Test
	void updateCustomerById() {
		Customer customer = customerRepository.findAll().get(0);
		CustomerDTO  customerDTO = customerMapper.customerToCustomerDto(customer);
		
		customerDTO.setId(null);
		customerDTO.setVersion(null);
		final String customerName = "UPDATED"; 
		customerDTO.setCustomerName(customerName);
		
		ResponseEntity responseEntity = controller.updateCustomer(customer.getId(), customerDTO);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
		
		Customer updatedCustomer = customerRepository.findById(customer.getId()).get();
		assertThat(updatedCustomer.getCustomerName()).isEqualTo(customerName);
	}
	
	@Test
	void updateNotFound() {
		assertThrows(NotFoundException.class, () -> {
			controller.updateCustomer(UUID.randomUUID(), CustomerDTO.builder().build());
		});
	}
}
