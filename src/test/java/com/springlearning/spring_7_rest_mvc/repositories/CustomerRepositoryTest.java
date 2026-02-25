package com.springlearning.spring_7_rest_mvc.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.springlearning.spring_7_rest_mvc.entities.Customer;

@DataJpaTest
public class CustomerRepositoryTest {

	@Autowired
	CustomerRepository customerRepository;
	
	@Test
	void testSaveCustomer() {
		Customer savedCustomer = customerRepository.save(Customer.builder()
				.customerName("customerName1")
				.build());
		
		assertThat(savedCustomer).isNotNull();
		assertThat(savedCustomer.getId()).isNotNull();
	}
}
