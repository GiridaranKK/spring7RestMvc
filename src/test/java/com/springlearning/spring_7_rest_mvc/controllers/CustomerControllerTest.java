package com.springlearning.spring_7_rest_mvc.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.springlearning.spring_7_rest_mvc.services.BeerServiceImpl;
import com.springlearning.spring_7_rest_mvc.services.CustomerService;
import com.springlearning.spring_7_rest_mvc.services.CustomerServiceImpl;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import com.springlearning.spring_7_rest_mvc.model.Customer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.UUID;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@MockitoBean
	CustomerService customerService;
	
	@Autowired
	ObjectMapper objectMapper;
	
	CustomerServiceImpl customerServiceImpl;
	
	@BeforeEach
	void setUp() {
		customerServiceImpl = new CustomerServiceImpl();
	}
	
	@Test
	void getCustomerById() throws Exception {
		Customer testCustomer = customerServiceImpl.listCustomers().get(0);
		
		given(customerService.getCustomerById(testCustomer.getId())).willReturn(testCustomer);
		mockMvc.perform(get("/api/v1/customer/" + testCustomer.getId())
				.accept(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk())
		        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		        .andExpect(jsonPath("$.id", is(testCustomer.getId().toString())))
		        .andExpect(jsonPath("$.customerName", is(testCustomer.getCustomerName())));
	}
	
	@Test
	void getAllCustomers() throws Exception {
		
		given(customerService.listCustomers()).willReturn(customerServiceImpl.listCustomers());
		
		mockMvc.perform(get("/api/v1/customer")
				.accept(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk())
		        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		        .andExpect(jsonPath("$.length()", is(3)));
	}
	
	@Test
	void testCreateNewCustomer() throws Exception {
		Customer customer = customerServiceImpl.listCustomers().get(0);
		customer.setId(null);
		customer.setVersion(null);
		given(customerService.saveCustomer(any(Customer.class))).willReturn(customerServiceImpl.listCustomers().get(1));
		
		mockMvc.perform(post("/api/v1/customer")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(customer)))
		        .andExpect(status().isCreated())
		        .andExpect(header().exists("Location"));
	}
	
	@Test
	void updateCustomerById() throws JacksonException, Exception {
		Customer customer = customerServiceImpl.listCustomers().get(0);
		
		mockMvc.perform(put("/api/v1/customer/" + customer.getId())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(customer)))
				.andExpect(status().isNoContent());
		
		verify(customerService).updateCustomer(any(UUID.class), any(Customer.class));
	}
	
	@Test
	void deleteCustomerById() throws Exception {
		Customer customer = customerServiceImpl.listCustomers().get(0);
		
		mockMvc.perform(delete("/api/v1/customer/" + customer.getId())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
		
		ArgumentCaptor<UUID> uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
		verify(customerService).deleteCustomerById(uuidArgumentCaptor.capture());
		assertThat(customer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
		
	}
}
