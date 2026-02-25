package com.springlearning.spring_7_rest_mvc.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

	@Id
	private UUID id;
	private String customerName;
	@Version
	private Integer version;
	private LocalDateTime createdDate;
	private LocalDateTime lastModifieddate;
}
