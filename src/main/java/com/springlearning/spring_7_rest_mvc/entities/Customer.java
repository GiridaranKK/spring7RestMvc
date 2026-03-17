package com.springlearning.spring_7_rest_mvc.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
	@GeneratedValue(generator = "UUID")
//	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@UuidGenerator
	@Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
	@JdbcTypeCode(SqlTypes.CHAR)
	private UUID id;
	@NotBlank
	@NotNull
	@Size(max = 60)
	@Column(length = 60)
	private String customerName;
	@Version
	private Integer version;
	private LocalDateTime createdDate;
	private LocalDateTime lastModifieddate;
}
