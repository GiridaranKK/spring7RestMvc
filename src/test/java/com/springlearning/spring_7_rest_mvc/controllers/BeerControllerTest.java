package com.springlearning.spring_7_rest_mvc.controllers;


import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.springlearning.spring_7_rest_mvc.services.BeerService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//@SpringBootTest
@WebMvcTest(BeerController.class)
public class BeerControllerTest {
	
//	@Autowired
//	BeerController beerController;
	@Autowired
	MockMvc mockMvc;
	
	@MockitoBean
	BeerService beerService;

	@Test
	void getBeerById() throws Exception {
//		System.out.println(beerController.getBeerById(UUID.randomUUID()));
		
		mockMvc.perform(get("/api/v1/beer/" + UUID.randomUUID())
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
}
