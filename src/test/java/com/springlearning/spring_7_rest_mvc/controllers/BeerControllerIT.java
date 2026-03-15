package com.springlearning.spring_7_rest_mvc.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import com.springlearning.spring_7_rest_mvc.entities.Beer;
import com.springlearning.spring_7_rest_mvc.model.BeerDTO;
import com.springlearning.spring_7_rest_mvc.repositories.BeerRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
public class BeerControllerIT {

	@Autowired
	BeerController beerController;
	
	@Autowired
	BeerRepository beerRepository;
	
	@Test
	void testListBeers() {
		List<BeerDTO> dtos = beerController.listBeers();
		
		assertThat(dtos.size()).isEqualTo(3);
	}
	
	@Rollback
	@Transactional
	@Test
	void testEmptyList() {
		beerRepository.deleteAll();
		List<BeerDTO> dtos = beerController.listBeers();
		
		assertThat(dtos.size()).isEqualTo(0);
	}
	
	@Test
	void testBeerById() {
		Beer beer = beerRepository.findAll().get(0);
		
		BeerDTO dto = beerController.getBeerById(beer.getId());
		
		assertThat(dto).isNotNull();
	}
	
	@Test
	void testBeerIdNullable() {
		assertThrows(NotFoundException.class, () -> {
			BeerDTO dto = beerController.getBeerById(UUID.randomUUID());
		});	
	}
	
	@Rollback
	@Transactional
	@Test
	void saveNewBeer() {
		BeerDTO beerDTO = BeerDTO.builder()
				.beerName("beerName4")
				.build();
		
		ResponseEntity responseEntity = beerController.handlePost(beerDTO);
		
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
		assertThat(responseEntity.getHeaders().getLocation()).isNotNull();
		String[] location = responseEntity.getHeaders().getLocation().getPath().split("/");
		UUID savedUUID = UUID.fromString(location[4]);
		
		Beer beer = beerRepository.findById(savedUUID).get();
		assertThat(beer).isNotNull();
	}
}
