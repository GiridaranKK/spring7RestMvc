package com.springlearning.spring_7_rest_mvc.mappers;

import org.mapstruct.Mapper;

import com.springlearning.spring_7_rest_mvc.entities.Beer;
import com.springlearning.spring_7_rest_mvc.model.BeerDTO;

@Mapper
public interface BeerMapper {

	Beer beerDtoToBeer(BeerDTO dto);
	
	BeerDTO BeerToBeerDto(Beer beer);
}
