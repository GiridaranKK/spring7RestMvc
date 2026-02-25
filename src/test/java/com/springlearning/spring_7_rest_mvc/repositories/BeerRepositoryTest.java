package com.springlearning.spring_7_rest_mvc.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.springlearning.spring_7_rest_mvc.entities.Beer;

@DataJpaTest
public class BeerRepositoryTest {

	@Autowired
	BeerRepository beerRepository;
	
	@Test
	void testSaveBeer() {
		Beer savedBeer = beerRepository.save(Beer.builder()
				.beerName("beerName1")
				.build());
		
		assertThat(savedBeer).isNotNull();
		assertThat(savedBeer.getId()).isNotNull();
	}
}
