package com.springlearning.spring_7_rest_mvc.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import com.springlearning.spring_7_rest_mvc.model.BeerCSVRecord;

@SpringBootTest
public class BeerCsvServiceImplTest {
	
	BeerCsvService beerCsvService = new BeerCsvServiceImpl();
	
	@Test
	void convertCsv() throws FileNotFoundException {
		File file = ResourceUtils.getFile("classpath:csvdata/beers.csv");
		List<BeerCSVRecord> recs = beerCsvService.convertCSV(file);
		
		System.out.println(recs.size());
		assertThat(recs.size()).isGreaterThan(0);
	}

}
