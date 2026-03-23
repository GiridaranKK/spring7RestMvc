package com.springlearning.spring_7_rest_mvc.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.springlearning.spring_7_rest_mvc.entities.Beer;
import com.springlearning.spring_7_rest_mvc.mappers.BeerMapper;
import com.springlearning.spring_7_rest_mvc.model.BeerDTO;
import com.springlearning.spring_7_rest_mvc.model.BeerStyle;
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
	public List<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventory) {
		// TODO Auto-generated method stub
		List<Beer> beerList;
		if(StringUtils.hasText(beerName) && beerStyle == null) {
			beerList = listBeersByName(beerName);
		}
		else if(!StringUtils.hasText(beerName) && beerStyle != null) {
			beerList = listBeersByStyle(beerStyle);
		}
		else if(StringUtils.hasText(beerName) && beerStyle != null) {
			beerList = listBeersByStyleAndName(beerName,beerStyle);
		}
		else {
			beerList = beerRepository.findAll();
		}
		
		if(showInventory != null && !showInventory) {
			beerList.forEach(beer -> beer.setQuantityOnHand(null));
		}
		return beerList.stream()
				.map(beerMapper::BeerToBeerDto)
				.collect(Collectors.toList());
	}
	
	private List<Beer> listBeersByStyleAndName(String beerName, BeerStyle beerStyle) {
		return beerRepository.findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle("%"+beerName+"%", beerStyle);
	}

	public List<Beer> listBeersByName(String beerName){
		return beerRepository.findAllByBeerNameIsLikeIgnoreCase("%"+beerName+"%");
		
	}
	
	public List<Beer> listBeersByStyle(BeerStyle beerStyle){
		return beerRepository.findAllByBeerStyle(beerStyle);
		
	}

	@Override
	public BeerDTO saveNewBeer(BeerDTO beerDTO) {
		// TODO Auto-generated method stub
		return beerMapper.BeerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beerDTO)));
	}

	@Override
	public Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beerDTO) {
		AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference<>();
		
		beerRepository.findById(beerId).ifPresentOrElse(foundBeer -> {
			foundBeer.setBeerName(beerDTO.getBeerName());
			foundBeer.setBeerStyle(beerDTO.getBeerStyle());
			foundBeer.setUpc(beerDTO.getUpc());
			foundBeer.setPrice(beerDTO.getPrice());
			atomicReference.set(Optional.of(beerMapper.BeerToBeerDto(beerRepository.save(foundBeer))));
		}, ()  -> {
			atomicReference.set(Optional.empty());
		});
		return atomicReference.get();
		
	}

	@Override
	public Boolean deleteBeerBbyId(UUID beerId) {
		if(beerRepository.existsById(beerId)) {
			beerRepository.deleteById(beerId);
			return true;
		}
		return false;
	}

	@Override
	public Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beerDTO) {
		
		AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference();
		beerRepository.findById(beerId).ifPresentOrElse(foundBeer -> {
			if(StringUtils.hasText(beerDTO.getBeerName())) {
				foundBeer.setBeerName(beerDTO.getBeerName());
			}
			if(beerDTO.getBeerStyle() != null) {
				foundBeer.setBeerStyle(beerDTO.getBeerStyle());
			}
			if(beerDTO.getPrice() != null) {
				foundBeer.setPrice(beerDTO.getPrice());
			}
			if(beerDTO.getQuantityOnHand() != null) {
				foundBeer.setQuantityOnHand(beerDTO.getQuantityOnHand());
			}
			if(StringUtils.hasText(beerDTO.getUpc())) {
				foundBeer.setUpc(beerDTO.getUpc());
			}
			atomicReference.set(Optional.of(beerMapper.BeerToBeerDto(beerRepository.save(foundBeer))));
		}, () ->{
			atomicReference.set(Optional.empty());
		});
		return atomicReference.get();
		
		
	}

}
