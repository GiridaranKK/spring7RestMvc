package com.springlearning.spring_7_rest_mvc.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springlearning.spring_7_rest_mvc.model.Customer;
import com.springlearning.spring_7_rest_mvc.services.CustomerService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/customer")
public class CustomerController {

	private final CustomerService customerService;
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Customer> getAllCustomers(){
		return customerService.listCustomers();
		
	}
	@RequestMapping(value = "{customerId}",method = RequestMethod.GET)
	public Customer getCustomerByID(@PathVariable("customerId") UUID customerId) {
		return customerService.getCustomerById(customerId);
		
	}
	
	@PostMapping
	public ResponseEntity saveCustomer(@RequestBody Customer customer) {
		Customer savedCustomer = customerService.saveCustomer(customer);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", "/api/v1/customer/" + savedCustomer.getId().toString());
		return new ResponseEntity(headers,HttpStatus.CREATED);
		
	}
}
