package com.springlearning.spring_7_rest_mvc.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Beer {

	private UUID id;
	private Integer version;
	private String beerName;
	private BeerStyle beerStyle;
	private String upc;
	private String quantityOnHand;
	private BigDecimal price;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;
	
}
