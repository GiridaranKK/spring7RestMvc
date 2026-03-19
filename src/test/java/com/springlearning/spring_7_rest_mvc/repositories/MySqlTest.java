package com.springlearning.spring_7_rest_mvc.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.springlearning.spring_7_rest_mvc.entities.Beer;

@Testcontainers
@SpringBootTest
@ActiveProfiles("localmysql")
public class MySqlTest {
	
	@Container
	static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0.36");
	
	@DynamicPropertySource
	static void mySqlProperties(DynamicPropertyRegistry registry) {
	    registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
	    registry.add("spring.datasource.username", mySQLContainer::getUsername);
	    registry.add("spring.datasource.password", mySQLContainer::getPassword);
	}
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	BeerRepository beerRepository;
	
	@Test
	void testAllBeers() {
		List<Beer> beers = beerRepository.findAll();
		
		assertThat(beers.size()).isGreaterThan(0);
	}

}
