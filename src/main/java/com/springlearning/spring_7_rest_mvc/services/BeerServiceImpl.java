package com.springlearning.spring_7_rest_mvc.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.springlearning.spring_7_rest_mvc.model.Beer;
import com.springlearning.spring_7_rest_mvc.model.BeerStyle;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BeerServiceImpl implements BeerService{

	@Override
	public Beer getBeerById(UUID id) {
		log.debug("Get Beer Id in service was called");
		return Beer.builder().id(id).version(1).beerName("beer1")
				.beerStyle(BeerStyle.BEERSTRYLE1).upc("1234")
				.price(new BigDecimal("122")).createdDate(LocalDateTime.now())
				.updatedDate(LocalDateTime.now()).build();
	}

}
