package com.springlearning.spring_7_rest_mvc.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.springlearning.spring_7_rest_mvc.entities.Beer;
import com.springlearning.spring_7_rest_mvc.model.BeerStyle;

public interface BeerRepository extends JpaRepository<Beer, UUID>{

	List<Beer> findAllByBeerNameIsLikeIgnoreCase(String beername);
	
	List<Beer> findAllByBeerStyle(BeerStyle beerStyle);
	
	List<Beer> findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle(String beername, BeerStyle beerStyle);
}
