package com.springlearning.spring_7_rest_mvc.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springlearning.spring_7_rest_mvc.model.CustomerDTO;
import com.springlearning.spring_7_rest_mvc.services.CustomerService;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
//@RequestMapping("api/v1/customer")
public class CustomerController {
	
	public static final String CUSTOMER_PATH = "/api/v1/customer";
	public static final String CUSTOMER_PATH_ID = CUSTOMER_PATH + "/{customerId}";

	private final CustomerService customerService;
	
	@GetMapping(CUSTOMER_PATH)
	public List<CustomerDTO> getAllCustomers(){
		return customerService.listCustomers();
		
	}
	@GetMapping(CUSTOMER_PATH_ID)
	public CustomerDTO getCustomerByID(@PathVariable("customerId") UUID customerId) {
		return customerService.getCustomerById(customerId).orElseThrow(NotFoundException::new);
		
	}
	
	@PostMapping(CUSTOMER_PATH)
	public ResponseEntity saveCustomer(@RequestBody CustomerDTO customerDTO) {
		CustomerDTO savedCustomer = customerService.saveCustomer(customerDTO);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", "/api/v1/customer/" + savedCustomer.getId().toString());
		return new ResponseEntity(headers,HttpStatus.CREATED);
		
	}
	
	@PutMapping(CUSTOMER_PATH_ID)
	public ResponseEntity updateCustomer(@PathVariable("customerId") UUID customerId,@RequestBody CustomerDTO customerDTO) {
		if(customerService.updateCustomer(customerId,customerDTO).isEmpty()) {
			throw new NotFoundException();
		}
		return new ResponseEntity(HttpStatus.NO_CONTENT);
		
	}
	
	@DeleteMapping(CUSTOMER_PATH_ID)
	public ResponseEntity deleteById(@PathVariable("customerId") UUID customerId) {
		if(!customerService.deleteCustomerById(customerId)) {
			throw new NotFoundException();
		}
		return new ResponseEntity(HttpStatus.NO_CONTENT);
		
	}
	
	@PatchMapping(CUSTOMER_PATH_ID)
	public ResponseEntity updateCustomerPatchById(@PathVariable("customerId") UUID customerId,@RequestBody CustomerDTO customerDTO) {
		customerService.patchCustomerById(customerId,customerDTO);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
}
