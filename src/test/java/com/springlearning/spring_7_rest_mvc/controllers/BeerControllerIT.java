package com.springlearning.spring_7_rest_mvc.controllers;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.annotation.Inherited;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.jayway.jsonpath.JsonPath;
import com.springlearning.spring_7_rest_mvc.entities.Beer;
import com.springlearning.spring_7_rest_mvc.mappers.BeerMapper;
import com.springlearning.spring_7_rest_mvc.model.BeerDTO;
import com.springlearning.spring_7_rest_mvc.repositories.BeerRepository;

import jakarta.transaction.Transactional;
import tools.jackson.databind.ObjectMapper;
import static org.hamcrest.core.Is.is;

@SpringBootTest
public class BeerControllerIT {

	@Autowired
	BeerController beerController;
	
	@Autowired
	BeerRepository beerRepository;
	
	@Autowired
	BeerMapper beerMapper;
	
	@Autowired
	WebApplicationContext wac;
	
	@Autowired
	ObjectMapper objectMapper;
	
	MockMvc mockMvc;
	
	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
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
	
	@Rollback
	@Transactional
	@Test
	void updateBeerByID() {
		Beer beer = beerRepository.findAll().get(0);
		BeerDTO beerDTO = beerMapper.BeerToBeerDto(beer);
		beerDTO.setId(null);
		beerDTO.setVersion(null);
		final String beerName = "UPDATED";
		beerDTO.setBeerName(beerName);
		
		ResponseEntity responseEntity = beerController.updateById(beer.getId(), beerDTO);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
		Beer updatedBeer = beerRepository.findById(beer.getId()).get();
		assertThat(updatedBeer.getBeerName()).isEqualTo(beerName);
	}
	
	@Test
	void updateNotFound() {
		assertThrows(NotFoundException.class, () -> {
			beerController.updateById(UUID.randomUUID(), BeerDTO.builder().build());
		});
	}
	
	@Transactional
	@Rollback
	@Test
	void deleteBeerById() {
		Beer beer = beerRepository.findAll().get(0);
		ResponseEntity responseEntity = beerController.deleteById(beer.getId());
		
		assertThat(beerRepository.findById(beer.getId()).isEmpty());
//		Beer foundBeer = beerRepository.findById(beer.getId()).get();
//		assertThat(foundBeer).isNull();
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
	}
	
	@Test
	void deleteNotFound() {
		assertThrows(NotFoundException.class, () -> {
			beerController.deleteById(UUID.randomUUID());
		});
	}
	
	@Test
	void testPatchBeerBadName() throws Exception {
		
		Beer beer = beerRepository.findAll().get(0);
		Map<String, Object> beerMap = new HashMap<>();
		beerMap.put("beerName", "New Namejkdssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssshjsdddddddddddddddddddddddddddddddddddddddd");
		
		MvcResult result = mockMvc.perform(patch(BeerController.BEER_PATH_ID,beer.getId())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(beerMap)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.length()", is(1)))
				.andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
	}
}
