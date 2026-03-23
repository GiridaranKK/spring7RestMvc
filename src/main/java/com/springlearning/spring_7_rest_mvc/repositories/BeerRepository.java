package com.springlearning.spring_7_rest_mvc.repositories;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.springlearning.spring_7_rest_mvc.entities.Beer;
import com.springlearning.spring_7_rest_mvc.model.BeerStyle;

public interface BeerRepository extends JpaRepository<Beer, UUID>{

	Page<Beer> findAllByBeerNameIsLikeIgnoreCase(String beername, Pageable pageable);
	
	Page<Beer> findAllByBeerStyle(BeerStyle beerStyle, Pageable pageable);
	
	Page<Beer> findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle(String beername, BeerStyle beerStyle, Pageable pageable);
}
