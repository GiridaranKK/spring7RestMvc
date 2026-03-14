package com.springlearning.spring_7_rest_mvc.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.springlearning.spring_7_rest_mvc.mappers.BeerMapper;
import com.springlearning.spring_7_rest_mvc.model.BeerDTO;
import com.springlearning.spring_7_rest_mvc.repositories.BeerRepository;

import lombok.RequiredArgsConstructor;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {
	
	private final BeerRepository beerRepository;
	private final BeerMapper beerMapper;
	

	@Override
	public Optional<BeerDTO> getBeerById(UUID id) {
		// TODO Auto-generated method stub
		return Optional.ofNullable(beerMapper.BeerToBeerDto(beerRepository.findById(id)
				.orElse(null)));
	}

	@Override
	public List<BeerDTO> listBeers() {
		// TODO Auto-generated method stub
		return beerRepository.findAll()
				.stream()
				.map(beerMapper::BeerToBeerDto)
				.collect(Collectors.toList());
	}

	@Override
	public BeerDTO saveNewBeer(BeerDTO beerDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateBeerById(UUID beerId, BeerDTO beerDTO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteBeerBbyId(UUID beerId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void patchBeerById(UUID beerId, BeerDTO beerDTO) {
		// TODO Auto-generated method stub
		
	}

}
