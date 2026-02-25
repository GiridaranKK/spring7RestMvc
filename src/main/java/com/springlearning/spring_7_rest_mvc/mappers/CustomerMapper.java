package com.springlearning.spring_7_rest_mvc.mappers;

import org.mapstruct.Mapper;

import com.springlearning.spring_7_rest_mvc.entities.Customer;
import com.springlearning.spring_7_rest_mvc.model.CustomerDTO;

@Mapper
public interface CustomerMapper {

	Customer customerDtoToCustomer(CustomerDTO dto);
	
	CustomerDTO customerToCustomerDto(Customer customer);
}
