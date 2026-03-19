package com.springlearning.spring_7_rest_mvc.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.springlearning.spring_7_rest_mvc.entities.Customer;
import com.springlearning.spring_7_rest_mvc.mappers.CustomerMapper;
import com.springlearning.spring_7_rest_mvc.model.BeerDTO;
import com.springlearning.spring_7_rest_mvc.model.CustomerDTO;
import com.springlearning.spring_7_rest_mvc.repositories.CustomerRepository;

import jakarta.transaction.Transactional;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest
public class CustomerControllerIT {

	@Autowired
	CustomerController controller;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	CustomerMapper customerMapper;
	
	@Autowired
	WebApplicationContext wac;
	
	@Autowired
	ObjectMapper objectMapper;
	
	MockMvc mockMvc;
	
	@BeforeEach
	void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
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
	
	@Test
	void deleteById() {
		Customer customer = customerRepository.findAll().get(0);
		ResponseEntity responseEntity = controller.deleteById(customer.getId());
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
		assertThat(customerRepository.findById(customer.getId()).isEmpty());
	}
	
	@Test
	void deleteNotFound() {
		assertThrows(NotFoundException.class, () -> {
			controller.deleteById(UUID.randomUUID());
		});
	}
	
	@Test
	void patchCustomerById() throws Exception {
		Customer customerDTO = customerRepository.findAll().get(0);
		Map<String, Object> customerMap = new HashMap<>();
		customerMap.put("customerName", "New Namedserrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrdsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
		
		MvcResult mvcResult = mockMvc.perform(patch(CustomerController.CUSTOMER_PATH_ID,customerDTO.getId())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(customerMap)))
		        .andExpect(status().isBadRequest())
		        .andExpect(jsonPath("$.length()", is(1)))
		        .andReturn();
		
		System.out.println(mvcResult.getResponse().getContentAsString());
		
	}
}
