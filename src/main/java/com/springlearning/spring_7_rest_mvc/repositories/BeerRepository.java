package com.springlearning.spring_7_rest_mvc.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.springlearning.spring_7_rest_mvc.entities.Beer;

public interface BeerRepository extends JpaRepository<Beer, UUID>{

}
