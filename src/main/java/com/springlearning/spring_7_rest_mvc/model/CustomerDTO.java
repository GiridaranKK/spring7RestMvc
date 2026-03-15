package com.springlearning.spring_7_rest_mvc.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

	private UUID id;
	
	@NotBlank
	@NotNull
	private String customerName;
	private Integer version;
	private LocalDateTime createdDate;
	private LocalDateTime lastModifieddate;
}
