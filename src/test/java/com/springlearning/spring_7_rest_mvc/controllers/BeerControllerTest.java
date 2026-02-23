package com.springlearning.spring_7_rest_mvc.controllers;


import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebProperties.Resources.Chain.Strategy.Content;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.springlearning.spring_7_rest_mvc.services.BeerService;
import com.springlearning.spring_7_rest_mvc.services.BeerServiceImpl;
import com.jayway.jsonpath.JsonPath;
import com.springlearning.spring_7_rest_mvc.model.Beer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
//@SpringBootTest
@WebMvcTest(BeerController.class)
public class BeerControllerTest {
	
//	@Autowired
//	BeerController beerController;
	@Autowired
	MockMvc mockMvc;
	
	@MockitoBean
	BeerService beerService;
	
	BeerServiceImpl beerServiceImpl = new BeerServiceImpl();

	@Test
	void getBeerById() throws Exception {
		
		Beer testBeer =  beerServiceImpl.listBeers().get(0);
//		System.out.println(beerController.getBeerById(UUID.randomUUID()));
		given(beerService.getBeerById(testBeer.getId())).willReturn(testBeer);
		mockMvc.perform(get("/api/v1/beer/" + testBeer.getId())
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id", is(testBeer.getId().toString())))
			.andExpect(jsonPath("$.beerName", is(testBeer.getBeerName())));
	}
	
	
}
