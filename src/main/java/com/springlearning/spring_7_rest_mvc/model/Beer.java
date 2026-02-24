package com.springlearning.spring_7_rest_mvc.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tools.jackson.databind.annotation.JsonDeserialize;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@JsonDeserialize(builder = Beer.BeerBuilder.class)
public class Beer {

	private UUID id;
	private Integer version;
//	@JsonProperty("beerName")
	private String beerName;
	private BeerStyle beerStyle;
	private String upc;
	private String quantityOnHand;
	private BigDecimal price;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;
	
}
