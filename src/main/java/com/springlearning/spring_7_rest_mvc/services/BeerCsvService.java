package com.springlearning.spring_7_rest_mvc.services;

import java.io.File;
import java.util.List;

import com.springlearning.spring_7_rest_mvc.model.BeerCSVRecord;

public interface BeerCsvService {
	List<BeerCSVRecord> convertCSV(File csvFile);
}
