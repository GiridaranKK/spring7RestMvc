package com.springlearning.spring_7_rest_mvc.services;

import java.util.List;
import java.util.UUID;

import com.springlearning.spring_7_rest_mvc.model.Beer;

public interface BeerService {

	Beer getBeerById(UUID id);

	List<Beer> listBeers();
}
