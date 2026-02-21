package com.springlearning.spring_7_rest_mvc.services;

import java.util.List;
import java.util.UUID;

import com.springlearning.spring_7_rest_mvc.model.Customer;

public interface CustomerService {
	List<Customer> listCustomers();
	Customer getCustomerById(UUID id);
}
