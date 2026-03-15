package com.springlearning.spring_7_rest_mvc.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
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

import com.springlearning.spring_7_rest_mvc.mappers.CustomerMapper;
import com.springlearning.spring_7_rest_mvc.model.CustomerDTO;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@WebMvcTest(CustomerController.class)
@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@MockitoBean
	CustomerService customerService;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Captor
	ArgumentCaptor<UUID> uuidArgumentCaptor;
	
	@Captor
	ArgumentCaptor<CustomerDTO> customerArgumentCaptor;
	
	CustomerServiceImpl customerServiceImpl;
	
	@BeforeEach
	void setUp() {
		customerServiceImpl = new CustomerServiceImpl();
	}
	
	@Test
	void getCustomerById() throws Exception {
		CustomerDTO testCustomer = customerServiceImpl.listCustomers().get(0);
		
		given(customerService.getCustomerById(testCustomer.getId())).willReturn(Optional.of(testCustomer));
		mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ID,testCustomer.getId())
				.accept(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk())
		        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		        .andExpect(jsonPath("$.id", is(testCustomer.getId().toString())))
		        .andExpect(jsonPath("$.customerName", is(testCustomer.getCustomerName())));
	}
	
	@Test
	void getAllCustomers() throws Exception {
		
		given(customerService.listCustomers()).willReturn(customerServiceImpl.listCustomers());
		
		mockMvc.perform(get(CustomerController.CUSTOMER_PATH)
				.accept(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk())
		        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		        .andExpect(jsonPath("$.length()", is(3)));
	}
	
	@Test
	void testCreateNewCustomer() throws Exception {
		CustomerDTO customerDTO = customerServiceImpl.listCustomers().get(0);
		customerDTO.setId(null);
		customerDTO.setVersion(null);
		given(customerService.saveCustomer(any(CustomerDTO.class))).willReturn(customerServiceImpl.listCustomers().get(1));
		
		mockMvc.perform(post(CustomerController.CUSTOMER_PATH)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(customerDTO)))
		        .andExpect(status().isCreated())
		        .andExpect(header().exists("Location"));
	}
	
	@Test
	void updateCustomerById() throws JacksonException, Exception {
		CustomerDTO customerDTO = customerServiceImpl.listCustomers().get(0);
		
		given(customerService.updateCustomer(any(), any())).willReturn(Optional.of(customerDTO));
		mockMvc.perform(put(CustomerController.CUSTOMER_PATH_ID,customerDTO.getId())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(customerDTO)))
				.andExpect(status().isNoContent());
		
		verify(customerService).updateCustomer(any(UUID.class), any(CustomerDTO.class));
	}
	
	@Test
	void deleteCustomerById() throws Exception {
		CustomerDTO customerDTO = customerServiceImpl.listCustomers().get(0);
		given(customerService.deleteCustomerById(any())).willReturn(true);
		mockMvc.perform(delete(CustomerController.CUSTOMER_PATH_ID,customerDTO.getId())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
		
//		ArgumentCaptor<UUID> uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
		verify(customerService).deleteCustomerById(uuidArgumentCaptor.capture());
		assertThat(customerDTO.getId()).isEqualTo(uuidArgumentCaptor.getValue());
		
	}
	
	@Test
	void patchCustomerById() throws JacksonException, Exception {
		CustomerDTO customerDTO = customerServiceImpl.listCustomers().get(0);
		Map<String, Object> customerMap = new HashMap<>();
		customerMap.put("customerName", "New Name");
		
		mockMvc.perform(patch(CustomerController.CUSTOMER_PATH_ID,customerDTO.getId())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(customerMap)))
		        .andExpect(status().isNoContent());
		
		verify(customerService).patchCustomerById(uuidArgumentCaptor.capture(), customerArgumentCaptor.capture());
		assertThat(customerDTO.getId()).isEqualTo(uuidArgumentCaptor.getValue());
		assertThat(customerMap.get("customerName")).isEqualTo(customerArgumentCaptor.getValue().getCustomerName());
	}
	
	@Test
	void customerByIdNotFound() throws Exception {
		
		given(customerService.getCustomerById(any(UUID.class))).willReturn(Optional.empty());
		mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ID,UUID.randomUUID()))
		.andExpect(status().isNotFound());
	}
	
	@Test
	void createCustomerNull() throws Exception {
		CustomerDTO customerDTO = CustomerDTO.builder().build();
		
		given(customerService.saveCustomer(any(CustomerDTO.class))).willReturn(customerServiceImpl.listCustomers().get(1));
		
		mockMvc.perform(post(CustomerController.CUSTOMER_PATH)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(customerDTO))
				).andExpect(status().isBadRequest());
	}
}
