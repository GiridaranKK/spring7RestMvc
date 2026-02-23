package com.springlearning.spring_7_rest_mvc.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.springlearning.spring_7_rest_mvc.services.BeerServiceImpl;
import com.springlearning.spring_7_rest_mvc.services.CustomerService;
import com.springlearning.spring_7_rest_mvc.services.CustomerServiceImpl;
import com.springlearning.spring_7_rest_mvc.model.Customer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.hamcrest.core.Is.is;

import static org.mockito.BDDMockito.given;

import java.util.UUID;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@MockitoBean
	CustomerService customerService;
	
	CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl();
	
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
}
