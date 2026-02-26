package com.springlearning.spring_7_rest_mvc.bootstrap;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.springlearning.spring_7_rest_mvc.entities.Beer;
import com.springlearning.spring_7_rest_mvc.entities.Customer;
import com.springlearning.spring_7_rest_mvc.model.BeerDTO;
import com.springlearning.spring_7_rest_mvc.model.BeerStyle;
import com.springlearning.spring_7_rest_mvc.model.CustomerDTO;
import com.springlearning.spring_7_rest_mvc.repositories.BeerRepository;
import com.springlearning.spring_7_rest_mvc.repositories.CustomerRepository;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner{
	
	private final BeerRepository beerRepository;
	private final CustomerRepository customerRepository;
	
	@Override
	public void run(String... args) throws Exception {
		loadBeerData();
		loadCustomerData();
	}
	
	private void loadBeerData() {
		
		if(beerRepository.count()==0) {
			Beer beer1 = Beer.builder()
					.beerName("beername1")
					.beerStyle(BeerStyle.BEERSTRYLE1)
					.upc("1234")
					.quantityOnHand("90")
					.price(new BigDecimal("122"))
					.createdDate(LocalDateTime.now())
					.updatedDate(LocalDateTime.now())
					.build();
			Beer beer2 = Beer.builder()
					.beerName("beername2")
					.beerStyle(BeerStyle.BEERSTYLE2)
					.upc("5678")
					.quantityOnHand("878")
					.price(new BigDecimal("125"))
					.createdDate(LocalDateTime.now())
					.updatedDate(LocalDateTime.now())
					.build();
			Beer beer3 = Beer.builder()
					.beerName("beername3")
					.beerStyle(BeerStyle.BEERSTYLE3)
					.upc("9012")
					.quantityOnHand("533")
					.price(new BigDecimal("99.8"))
					.createdDate(LocalDateTime.now())
					.updatedDate(LocalDateTime.now())
					.build();
			
			beerRepository.save(beer1);
			beerRepository.save(beer2);
			beerRepository.save(beer3);
		}
		
	}

	private void loadCustomerData() {
		
		if(customerRepository.count()==0) {
			Customer customer1 = Customer.builder()		
					.customerName("CustomerName1")
					.createdDate(LocalDateTime.now())
					.lastModifieddate(LocalDateTime.now())
					.build();
			Customer customer2 = Customer.builder()
					.customerName("CustomerName2")
					.createdDate(LocalDateTime.now())
					.lastModifieddate(LocalDateTime.now())
					.build();
			Customer customer3 = Customer.builder()
					.customerName("CustomerName3")
					.createdDate(LocalDateTime.now())
					.lastModifieddate(LocalDateTime.now())
					.build();
			
//			customerRepository.save(customer1);
//			customerRepository.save(customer2);
//			customerRepository.save(customer3);
			customerRepository.saveAll(Arrays.asList(customer1,customer2,customer3));
		}
	}

}
