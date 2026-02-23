package com.springlearning.spring_7_rest_mvc.services;

import java.util.List;
import java.util.UUID;

import com.springlearning.spring_7_rest_mvc.model.Beer;

public interface BeerService {

	Beer getBeerById(UUID id);

	List<Beer> listBeers();

	Beer saveNewBeer(Beer beer);

	void updateBeerById(UUID beerId, Beer beer);

	void deleteBeerBbyId(UUID beerId);

	void patchBeerById(UUID beerId, Beer beer);
}
