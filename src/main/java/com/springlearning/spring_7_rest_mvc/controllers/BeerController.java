package com.springlearning.spring_7_rest_mvc.controllers;

import java.net.http.HttpRequest;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springlearning.spring_7_rest_mvc.model.Beer;
import com.springlearning.spring_7_rest_mvc.services.BeerService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
//@Controller
@RestController
@RequestMapping("api/v1/beer")
public class BeerController {

	private final BeerService beerService;

//	public BeerController(BeerService beerService) {
//		super();
//		this.beerService = beerService;
//	}
	@RequestMapping(value="{beerId}",method = RequestMethod.GET)
	public Beer getBeerById(@PathVariable("beerId") UUID beerId) {
		log.debug("get beer by id - in controller");
		return beerService.getBeerById(beerId);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Beer> listBeers(){
		return beerService.listBeers();
	}
	
	@PostMapping
//	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity handlePost(@RequestBody Beer beer) {
		Beer savedBeer = beerService.saveNewBeer(beer);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", "/api/v1/beer/"+savedBeer.getId().toString());
		return new ResponseEntity(headers, HttpStatus.CREATED);
		
	}
	
	
}
