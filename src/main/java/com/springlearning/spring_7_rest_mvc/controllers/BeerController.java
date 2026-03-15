package com.springlearning.spring_7_rest_mvc.controllers;

import java.net.http.HttpRequest;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springlearning.spring_7_rest_mvc.model.BeerDTO;
import com.springlearning.spring_7_rest_mvc.services.BeerService;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
//@Controller
@RestController
//@RequestMapping("api/v1/beer")
public class BeerController {
	
	public static final String BEER_PATH = "/api/v1/beer";
	public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";

	private final BeerService beerService;

//	public BeerController(BeerService beerService) {
//		super();
//		this.beerService = beerService;
//	}
//	@RequestMapping(value="{beerId}",method = RequestMethod.GET)
	@GetMapping(BEER_PATH_ID)
	public BeerDTO getBeerById(@PathVariable("beerId") UUID beerId) {
		log.debug("get beer by id - in controller");
		return beerService.getBeerById(beerId).orElseThrow(NotFoundException::new);
	}
	
//	@RequestMapping(method = RequestMethod.GET)
	@GetMapping(BEER_PATH)
	public List<BeerDTO> listBeers(){
		return beerService.listBeers();
	}
	
	@PostMapping(BEER_PATH)
//	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity handlePost(@RequestBody BeerDTO beerDTO) {
		BeerDTO savedBeer = beerService.saveNewBeer(beerDTO);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", "/api/v1/beer/"+savedBeer.getId().toString());
		return new ResponseEntity(headers, HttpStatus.CREATED);
		
	}
	
	@PutMapping(BEER_PATH_ID)
	public ResponseEntity updateById(@PathVariable("beerId") UUID beerId,@RequestBody BeerDTO beerDTO) {
		if(beerService.updateBeerById(beerId,beerDTO).isEmpty()){
			throw new NotFoundException();
		}
		return new ResponseEntity(HttpStatus.NO_CONTENT);
		
	}
	
	@DeleteMapping(BEER_PATH_ID)
	public ResponseEntity deleteById(@PathVariable("beerId") UUID beerId) {
		if(!beerService.deleteBeerBbyId(beerId)) {
			throw new NotFoundException();
		}
		return new ResponseEntity(HttpStatus.NO_CONTENT);
		
	}
	
	@PatchMapping(BEER_PATH_ID)
	public ResponseEntity updateBeerPatchById(@PathVariable("beerId") UUID beerId,@RequestBody BeerDTO beerDTO) {
		beerService.patchBeerById(beerId,beerDTO);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
	
//	@ExceptionHandler(NotFoundException.class)
//	public ResponseEntity handleNotFoundException() {
//		System.out.println("In Exception Handler");
//		return ResponseEntity.notFound().build();
//		
//	}
	
}
