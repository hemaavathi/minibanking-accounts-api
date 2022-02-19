package com.minibanking.account.api.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AccountException extends RuntimeException  {
	
	private int code;
	private String message;
	private HttpStatus httpStatus;

}