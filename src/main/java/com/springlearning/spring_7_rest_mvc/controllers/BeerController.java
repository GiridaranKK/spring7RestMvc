package com.springlearning.spring_7_rest_mvc.controllers;

import java.util.UUID;

import org.springframework.stereotype.Controller;

import com.springlearning.spring_7_rest_mvc.model.Beer;
import com.springlearning.spring_7_rest_mvc.services.BeerService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Controller
public class BeerController {

	private final BeerService beerService;

//	public BeerController(BeerService beerService) {
//		super();
//		this.beerService = beerService;
//	}
	
	public Beer getBeerById(UUID id) {
		log.debug("get beer by id - in controller");
		return beerService.getBeerById(id);
	}
	
	
}
