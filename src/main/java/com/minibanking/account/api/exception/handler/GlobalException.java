package com.minibanking.account.api.exception.handler;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.minibanking.account.api.exception.AccountException;
import com.minibanking.account.api.model.ErrorResponse;


@RestControllerAdvice
public class GlobalException {
	
	Logger logger = LoggerFactory.getLogger(GlobalException.class);
	
	@ExceptionHandler(AccountException.class)
	public ResponseEntity<Map<String,ErrorResponse>> handleAccountException(AccountException ae) {
		logger.error(ae.getMessage());
		ErrorResponse eResponse = new ErrorResponse(ae.getCode(),ae.getMessage());
		Map<String,ErrorResponse> responseMap = new HashMap<>();
		responseMap.put("error", eResponse);
		ResponseEntity<Map<String,ErrorResponse>> entity = new ResponseEntity<>(responseMap, ae.getHttpStatus());
		return entity;
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,ErrorResponse>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ce) {
		logger.error("MethodArgumentNotValidException occurred");
		ErrorResponse error = new ErrorResponse(2500, ce.getFieldError().getDefaultMessage());
		Map<String,ErrorResponse> eResponse = new HashMap<>();
		eResponse.put("error", error);
		ResponseEntity<Map<String,ErrorResponse>> entity = new ResponseEntity<>(eResponse, HttpStatus.BAD_REQUEST);
		return entity;
	}
		
	}

