package com.springlearning.spring_7_rest_mvc.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;

import com.springlearning.spring_7_rest_mvc.bootstrap.BootstrapData;
import com.springlearning.spring_7_rest_mvc.entities.Beer;
import com.springlearning.spring_7_rest_mvc.model.BeerStyle;
import com.springlearning.spring_7_rest_mvc.services.BeerCsvServiceImpl;

import jakarta.validation.ConstraintViolationException;

@DataJpaTest
@Import({BootstrapData.class, BeerCsvServiceImpl.class})
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
	
	@Test
	void testGetBeerListByName() {
		Page<Beer> list = beerRepository.findAllByBeerNameIsLikeIgnoreCase("%IPA%", null);
		assertThat(list.getContent().size()).isEqualTo(336);
	}
	
	@Test
	void testGetBeerListByStyle() {
		Page<Beer> list = beerRepository.findAllByBeerStyle(BeerStyle.PILSNER, null);
		assertThat(list.getContent().size()).isEqualTo(1160);
	}
	
}
