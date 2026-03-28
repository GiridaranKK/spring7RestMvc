package com.springlearning.spring_7_rest_mvc.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import com.springlearning.spring_7_rest_mvc.entities.Beer;
import com.springlearning.spring_7_rest_mvc.entities.Customer;

@SpringBootTest
public class BeerOrderRepositoryTest {

	@Autowired
	BeerOrderRepository beerOrderRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	BeerRepository beerRepository;
	
	Customer testCustomer;
	Beer testBeer;
	
	@BeforeEach
	void setUp() {
		testCustomer = customerRepository.findAll().get(0);
		testBeer = beerRepository.findAll().get(0);
	}
	
	@Test
	void testBeerOrders() {
		System.out.println(beerOrderRepository.count());
		System.out.println(customerRepository.count());
		System.out.println(beerRepository.count());
		System.out.println(testCustomer.getCustomerName());
		System.out.println(testBeer.getBeerName());
	}
}
