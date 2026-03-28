package com.springlearning.spring_7_rest_mvc.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import com.springlearning.spring_7_rest_mvc.model.BeerStyle;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class BeerOrder {

	 @Id
	 @GeneratedValue(generator = "UUID")
	 @UuidGenerator
	 @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
	 @JdbcTypeCode(SqlTypes.CHAR)
	 private UUID id;
	 
	 @Version
	 private long version;
	 
	 @CreationTimestamp
	 @Column(updatable = false)
	 private LocalDateTime createdDate;
	 
	 @UpdateTimestamp
	 private LocalDateTime lastModifiedDate;
	 
	 public boolean isNew() { return this.id == null; }
	 
	 private String customerRef;
	 
	 @ManyToOne
	 private Customer customer;
	 
	 @OneToMany(mappedBy = "beerOrder")
	 private Set<BeerOrderLine> beerOrderLines;
	 
}
