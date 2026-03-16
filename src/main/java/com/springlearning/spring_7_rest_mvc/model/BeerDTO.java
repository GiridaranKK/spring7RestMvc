package com.springlearning.spring_7_rest_mvc.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class BeerDTO {

	private UUID id;
	private Integer version;
//	@JsonProperty("beerName")
	@NotBlank
	@NotNull
	private String beerName;
	
	@NotNull
	private BeerStyle beerStyle;
	
	@NotNull
	@NotBlank
	private String upc;
	private String quantityOnHand;
	
	@NotNull
	private BigDecimal price;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;
	
}
