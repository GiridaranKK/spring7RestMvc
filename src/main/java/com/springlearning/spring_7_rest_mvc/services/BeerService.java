package com.springlearning.spring_7_rest_mvc.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.springlearning.spring_7_rest_mvc.model.BeerDTO;
import com.springlearning.spring_7_rest_mvc.model.BeerStyle;

public interface BeerService {

	Optional<BeerDTO> getBeerById(UUID id);

	Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventory, Integer pageNumber, Integer pageSize);

	BeerDTO saveNewBeer(BeerDTO beerDTO);

	Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beerDTO);

	Boolean deleteBeerBbyId(UUID beerId);

	Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beerDTO);
}
