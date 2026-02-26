package com.springlearning.spring_7_rest_mvc.bootstrap;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.springlearning.spring_7_rest_mvc.repositories.BeerRepository;
import com.springlearning.spring_7_rest_mvc.repositories.CustomerRepository;
import com.springlearning.spring_7_rest_mvc.services.BeerService;

@DataJpaTest
public class BootstrapDataTest {

	@Autowired
	BeerRepository beerRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	BootstrapData bootstrapData;
	
	@BeforeEach
	void setUp() {
		bootstrapData = new BootstrapData(beerRepository, customerRepository);
	}
	
	@Test
	void TestRun() throws Exception {
		bootstrapData.run(null);
		assertThat(beerRepository.count()).isEqualTo(3);
		assertThat(customerRepository.count()).isEqualTo(3);
	}
}
