package com.springlearning.spring_7_rest_mvc.repositories;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.springlearning.spring_7_rest_mvc.entities.Beer;
import com.springlearning.spring_7_rest_mvc.model.BeerStyle;

import jakarta.validation.ConstraintViolationException;

@DataJpaTest
public class BeerRepositoryTest {

	@Autowired
	BeerRepository beerRepository;
	
	@Test
	void testSaveBeer() {
		
		assertThrows(ConstraintViolationException.class, () ->{
			Beer savedBeer = beerRepository.save(Beer.builder()
					.beerName("beerName1 jksaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
					.beerStyle(BeerStyle.BEERSTRYLE1)
					.upc("1234566")
					.price(new BigDecimal(25.00))
					.build());
			beerRepository.flush();
		});
		
//		assertThat(savedBeer).isNotNull();
//		assertThat(savedBeer.getId()).isNotNull();
	}
}
