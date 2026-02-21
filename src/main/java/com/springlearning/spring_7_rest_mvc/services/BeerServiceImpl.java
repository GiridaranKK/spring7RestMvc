package com.springlearning.spring_7_rest_mvc.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.springlearning.spring_7_rest_mvc.model.Beer;
import com.springlearning.spring_7_rest_mvc.model.BeerStyle;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BeerServiceImpl implements BeerService{
	
	private Map<UUID, Beer> beerMap;

	public BeerServiceImpl() {
		this.beerMap = new HashMap<>();
		
		Beer beer1 = Beer.builder()
				.id(UUID.randomUUID())
				.version(1)
				.beerName("beername1")
				.beerStyle(BeerStyle.BEERSTRYLE1)
				.upc("1234")
				.quantityOnHand("90")
				.price(new BigDecimal("122"))
				.createdDate(LocalDateTime.now())
				.updatedDate(LocalDateTime.now())
				.build();
		Beer beer2 = Beer.builder()
				.id(UUID.randomUUID())
				.version(1)
				.beerName("beername2")
				.beerStyle(BeerStyle.BEERSTYLE2)
				.upc("5678")
				.quantityOnHand("878")
				.price(new BigDecimal("125"))
				.createdDate(LocalDateTime.now())
				.updatedDate(LocalDateTime.now())
				.build();
		Beer beer3 = Beer.builder()
				.id(UUID.randomUUID())
				.version(1)
				.beerName("beername3")
				.beerStyle(BeerStyle.BEERSTYLE3)
				.upc("9012")
				.quantityOnHand("533")
				.price(new BigDecimal("99.8"))
				.createdDate(LocalDateTime.now())
				.updatedDate(LocalDateTime.now())
				.build();
		
		beerMap.put(beer1.getId(), beer1);
		beerMap.put(beer2.getId(), beer2);
		beerMap.put(beer3.getId(), beer3);
	}
	
	@Override
	public List<Beer> listBeers(){
		return new ArrayList<>(beerMap.values());
	}
	
	@Override
	public Beer getBeerById(UUID id){
		return beerMap.get(id);
	}

	@Override
	public Beer saveNewBeer(Beer beer) {
		
		Beer savedBeer = Beer.builder()
				.id(UUID.randomUUID())
				.version(1)
				.beerName(beer.getBeerName())
				.beerStyle(beer.getBeerStyle())
				.upc(beer.getUpc())
				.quantityOnHand(beer.getQuantityOnHand())
				.price(beer.getPrice())
				.createdDate(LocalDateTime.now())
				.updatedDate(LocalDateTime.now())
				.build();
		
		beerMap.put(savedBeer.getId(), savedBeer);
		return savedBeer;
	}

//	@Override
//	public Beer getBeerById(UUID id) {
//		log.debug("Get Beer Id in service was called");
//		return Beer.builder().id(id).version(1).beerName("beer1")
//				.beerStyle(BeerStyle.BEERSTRYLE1).upc("1234")
//				.price(new BigDecimal("122")).createdDate(LocalDateTime.now())
//				.updatedDate(LocalDateTime.now()).build();
//	}

}
