package com.springlearning.spring_7_rest_mvc.controllers;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebProperties.Resources.Chain.Strategy.Content;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.springlearning.spring_7_rest_mvc.services.BeerService;
import com.springlearning.spring_7_rest_mvc.services.BeerServiceImpl;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import com.jayway.jsonpath.JsonPath;
import com.springlearning.spring_7_rest_mvc.model.Beer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
//@SpringBootTest
@WebMvcTest(BeerController.class)
@ExtendWith(MockitoExtension.class)
public class BeerControllerTest {
	
//	@Autowired
//	BeerController beerController;
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Captor
	ArgumentCaptor<UUID> uuidArgumentCaptor;
	
	@Captor // captor will not work in spring 7 need to add in @ExtendWith(MockitoExtension.class) in class
	ArgumentCaptor<Beer> beerArgumentCaptor;
	
	@MockitoBean
	BeerService beerService;
	
	BeerServiceImpl beerServiceImpl;
	
	
	@BeforeEach
	void setUp() {
		beerServiceImpl = new BeerServiceImpl();
	}

	@Test
	void getBeerById() throws Exception {
		
		Beer testBeer =  beerServiceImpl.listBeers().get(0);
//		System.out.println(beerController.getBeerById(UUID.randomUUID()));
		given(beerService.getBeerById(testBeer.getId())).willReturn(testBeer);
		mockMvc.perform(get(BeerController.BEER_PATH + "/"  + testBeer.getId())
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id", is(testBeer.getId().toString())))
			.andExpect(jsonPath("$.beerName", is(testBeer.getBeerName())));
	}
	
	@Test
	void testListBeers() throws Exception {
		given(beerService.listBeers()).willReturn(beerServiceImpl.listBeers());
		
		mockMvc.perform(get(BeerController.BEER_PATH)
				.accept(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk())
		        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		        .andExpect(jsonPath("$.length()", is(3)));
	}
	
	@Test
	void testCreateNewBeer() throws Exception {
		Beer beer = beerServiceImpl.listBeers().get(0);
//		System.out.println(objectMapper.writeValueAsString(beer));
		beer.setVersion(null);
		beer.setId(null);
		
		given(beerService.saveNewBeer(any(Beer.class))).willReturn(beerServiceImpl.listBeers().get(1));
		
		mockMvc.perform(post(BeerController.BEER_PATH)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(beer)))
				.andExpect(status().isCreated())
				.andExpect(header().exists("Location"));
		
		
	}
	
	@Test
	void testUpdateBeer() throws JacksonException, Exception {
		Beer beer = beerServiceImpl.listBeers().get(0);
		
		mockMvc.perform(put(BeerController.BEER_PATH + "/"  + beer.getId())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(beer)))
		        .andExpect(status().isNoContent());
		
		verify(beerService).updateBeerById(any(UUID.class), any(Beer.class));
	}
	
	@Test
	void testdeleteBeer() throws Exception {
		Beer beer = beerServiceImpl.listBeers().get(0);
		
		mockMvc.perform(delete(BeerController.BEER_PATH + "/"  + beer.getId())
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNoContent());
//		ArgumentCaptor<UUID> uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
		verify(beerService).deleteBeerBbyId(uuidArgumentCaptor.capture());
		
		assertThat(beer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
	}
	
	@Test
	void testPatchBeer() throws Exception {
		
		Beer beer = beerServiceImpl.listBeers().get(0);
		Map<String, Object> beerMap = new HashMap<>();
		beerMap.put("beerName", "New Name");
		
		mockMvc.perform(patch(BeerController.BEER_PATH + "/"  + beer.getId())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(beerMap)))
				.andExpect(status().isNoContent());
		verify(beerService).patchBeerById(uuidArgumentCaptor.capture(), beerArgumentCaptor.capture());
		assertThat(beer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
		assertThat(beerMap.get("beerName")).isEqualTo(beerArgumentCaptor.getValue().getBeerName());
	}
	
	
}
