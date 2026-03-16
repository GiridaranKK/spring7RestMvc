package com.springlearning.spring_7_rest_mvc.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class CustomeErrorController {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	ResponseEntity handleBindErrors(MethodArgumentNotValidException exception) {
		List errorList = exception.getFieldErrors().stream()
				.map(fieldErrors -> {
					Map<String,String> errorMap = new HashMap<>();
					errorMap.put(fieldErrors.getField(), fieldErrors.getDefaultMessage());
					return errorMap;
				}).collect(Collectors.toList());
		
		return ResponseEntity.badRequest().body(errorList);
		
	}
	
	@ExceptionHandler
	ResponseEntity handleJpaViolations(TransactionSystemException exception) {
		
		ResponseEntity.BodyBuilder responseEntity = ResponseEntity.badRequest();
		if(exception.getCause().getCause() instanceof ConstraintViolationException) {
			ConstraintViolationException constraintViolationException =(ConstraintViolationException) exception.getCause().getCause();
					List errors = constraintViolationException.getConstraintViolations().stream()
								  .map(constraintViolation ->{
									  Map<String,String> errorMap = new HashMap<>();
									  errorMap.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
									  return errorMap;
								  }).collect(Collectors.toList());
		return responseEntity.body(errors);
		}
		return responseEntity.build();
		
	}
}
