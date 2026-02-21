package com.springlearning.spring_7_rest_mvc.model;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

	private UUID id;
	private String customerName;
	private Integer version;
	private LocalDateTime createdDate;
	private LocalDateTime lastModifieddate;
}
