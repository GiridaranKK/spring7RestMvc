package com.springlearning.spring_7_rest_mvc.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.springlearning.spring_7_rest_mvc.model.BeerDTO;
import com.springlearning.spring_7_rest_mvc.model.BeerStyle;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BeerServiceImpl implements BeerService{
	
	private Map<UUID, BeerDTO> beerMap;

	public BeerServiceImpl() {
		this.beerMap = new HashMap<>();
		
		BeerDTO beer1 = BeerDTO.builder()
				.id(UUID.randomUUID())
				.version(1)
				.beerName("beername1")
				.beerStyle(BeerStyle.BEERSTRYLE1)
				.upc("1234")
				.quantityOnHand(90)
				.price(new BigDecimal("122"))
				.createdDate(LocalDateTime.now())
				.updatedDate(LocalDateTime.now())
				.build();
		BeerDTO beer2 = BeerDTO.builder()
				.id(UUID.randomUUID())
				.version(1)
				.beerName("beername2")
				.beerStyle(BeerStyle.BEERSTYLE2)
				.upc("5678")
				.quantityOnHand(878)
				.price(new BigDecimal("125"))
				.createdDate(LocalDateTime.now())
				.updatedDate(LocalDateTime.now())
				.build();
		BeerDTO beer3 = BeerDTO.builder()
				.id(UUID.randomUUID())
				.version(1)
				.beerName("beername3")
				.beerStyle(BeerStyle.BEERSTYLE3)
				.upc("9012")
				.quantityOnHand(533)
				.price(new BigDecimal("99.8"))
				.createdDate(LocalDateTime.now())
				.updatedDate(LocalDateTime.now())
				.build();
		
		beerMap.put(beer1.getId(), beer1);
		beerMap.put(beer2.getId(), beer2);
		beerMap.put(beer3.getId(), beer3);
	}
	
	@Override
	public List<BeerDTO> listBeers(){
		return new ArrayList<>(beerMap.values());
	}
	
	@Override
	public Optional<BeerDTO> getBeerById(UUID id){
		return Optional.of(beerMap.get(id));
	}

	@Override
	public BeerDTO saveNewBeer(BeerDTO beerDTO) {
		
		BeerDTO savedBeer = BeerDTO.builder()
				.id(UUID.randomUUID())
				.version(1)
				.beerName(beerDTO.getBeerName())
				.beerStyle(beerDTO.getBeerStyle())
				.upc(beerDTO.getUpc())
				.quantityOnHand(beerDTO.getQuantityOnHand())
				.price(beerDTO.getPrice())
				.createdDate(LocalDateTime.now())
				.updatedDate(LocalDateTime.now())
				.build();
		
		beerMap.put(savedBeer.getId(), savedBeer);
		return savedBeer;
	}

	@Override
	public Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beerDTO) {
		BeerDTO existingBeer = beerMap.get(beerId);
		existingBeer.setBeerName(beerDTO.getBeerName());
		existingBeer.setPrice(beerDTO.getPrice());
		existingBeer.setUpc(beerDTO.getUpc());
		existingBeer.setQuantityOnHand(beerDTO.getQuantityOnHand());
		
		beerMap.put(existingBeer.getId(), existingBeer);
		return Optional.of(existingBeer);
		
	}

	@Override
	public Boolean deleteBeerBbyId(UUID beerId) {
		beerMap.remove(beerId);
		return true;
	}

	@Override
	public Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beerDTO) {
		BeerDTO existingBeer = beerMap.get(beerId);
		if(StringUtils.hasText(beerDTO.getBeerName())) {
			existingBeer.setBeerName(beerDTO.getBeerName());
		}
		if(beerDTO.getBeerStyle() != null) {
			existingBeer.setBeerStyle(beerDTO.getBeerStyle());
		}
		if(beerDTO.getPrice() != null) {
			existingBeer.setPrice(beerDTO.getPrice());
		}
		if(beerDTO.getQuantityOnHand() != null) {
			existingBeer.setQuantityOnHand(beerDTO.getQuantityOnHand());
		}
		if(StringUtils.hasText(beerDTO.getUpc())) {
			existingBeer.setUpc(beerDTO.getUpc());
		}
		return Optional.of(existingBeer);
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
