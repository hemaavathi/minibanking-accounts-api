package com.minibanking.account.api.util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AccountUtility {

static Logger logger = LoggerFactory.getLogger(AccountUtility.class);
	
	public static boolean isValidDate(String date) {
		boolean validDate = false;
		try {
			LocalDate parsedDate = LocalDate.parse(date);
			validDate = true;			
		} catch(DateTimeParseException e){
			logger.error(e.getMessage());
		}
		return validDate;
		
	}
}
