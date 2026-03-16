package com.springlearning.spring_7_rest_mvc.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.springlearning.spring_7_rest_mvc.entities.Customer;

import jakarta.validation.ConstraintViolationException;

@DataJpaTest
public class CustomerRepositoryTest {

	@Autowired
	CustomerRepository customerRepository;
	
	@Test
	void testSaveCustomer() {
		
		assertThrows(ConstraintViolationException.class, () -> {
			Customer savedCustomer = customerRepository.save(Customer.builder()
					.customerName("sdlkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk")
					.build());
			customerRepository.flush();
		});
		
//		assertThat(savedCustomer).isNotNull();
//		assertThat(savedCustomer.getId()).isNotNull();
	}
}
