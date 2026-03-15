package com.springlearning.spring_7_rest_mvc.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.springlearning.spring_7_rest_mvc.model.BeerDTO;

public interface BeerService {

	Optional<BeerDTO> getBeerById(UUID id);

	List<BeerDTO> listBeers();

	BeerDTO saveNewBeer(BeerDTO beerDTO);

	Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beerDTO);

	Boolean deleteBeerBbyId(UUID beerId);

	void patchBeerById(UUID beerId, BeerDTO beerDTO);
}
