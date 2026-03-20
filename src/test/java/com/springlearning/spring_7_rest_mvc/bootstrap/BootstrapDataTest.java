package com.springlearning.spring_7_rest_mvc.bootstrap;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.springlearning.spring_7_rest_mvc.repositories.BeerRepository;
import com.springlearning.spring_7_rest_mvc.repositories.CustomerRepository;
import com.springlearning.spring_7_rest_mvc.services.BeerCsvService;
import com.springlearning.spring_7_rest_mvc.services.BeerCsvServiceImpl;
import com.springlearning.spring_7_rest_mvc.services.BeerService;

@DataJpaTest
@Import(BeerCsvServiceImpl.class)
public class BootstrapDataTest {

	@Autowired
	BeerRepository beerRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	BeerCsvService beerCsvService;
	
	BootstrapData bootstrapData;
	
	@BeforeEach
	void setUp() {
		bootstrapData = new BootstrapData(beerRepository, customerRepository,beerCsvService);
	}
	
	@Test
	void TestRun() throws Exception {
		bootstrapData.run(null);
		assertThat(beerRepository.count()).isEqualTo(2413);
		assertThat(customerRepository.count()).isEqualTo(3);
	}
}
