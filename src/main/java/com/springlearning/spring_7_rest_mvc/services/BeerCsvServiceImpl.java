package com.springlearning.spring_7_rest_mvc.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import org.springframework.stereotype.Service;

import com.opencsv.bean.CsvToBeanBuilder;
import com.springlearning.spring_7_rest_mvc.model.BeerCSVRecord;

@Service
public class BeerCsvServiceImpl implements BeerCsvService{

	@Override
	public List<BeerCSVRecord> convertCSV(File csvFile) {
		try {
			List<BeerCSVRecord> beerCSVRecords = new CsvToBeanBuilder<BeerCSVRecord>(new FileReader(csvFile))
					.withType(BeerCSVRecord.class)
					.build().parse();
			return beerCSVRecords;
		} catch (FileNotFoundException e) {
			throw new RuntimeException();
		}
	}

}
