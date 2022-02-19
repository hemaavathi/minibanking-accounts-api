package com.minibanking.account.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.minibanking.account.api.entity.Account;
import com.minibanking.account.api.exception.AccountException;
import com.minibanking.account.api.model.AccountRequest;
import com.minibanking.account.api.service.AccountService;

@RestController
public class AccountController {
	
	Logger logger = LoggerFactory.getLogger(AccountController.class);
	
	@Autowired
	private AccountService accountService;
	
	@RequestMapping("/accounts")
	public ResponseEntity<Map<String,List<Account>>> getAllAccounts(){
		logger.info("Entering getAllAccounts");
		List<Account> accList = accountService.getAllAccounts();
		Map<String,List<Account>> responseMap = new HashMap<>();
		responseMap.put("accountList", accList);
		ResponseEntity<Map<String,List<Account>>> entity = new ResponseEntity<>(responseMap, HttpStatus.OK);
		logger.info("Exiting getAllAccounts");
		return entity;
	}
	
	@RequestMapping("/account/{acId}")
	public ResponseEntity<Map<String, Account>> getAccountById(@PathVariable Long acId) {
		logger.info("Entering getAccountsById");
		Optional<Account> account = accountService.getAccountById(acId);
		Map<String, Account> responseMap = new HashMap<>();
		if(account.isPresent()) {
			responseMap.put("account", account.get());
		} else {
			throw new AccountException(4000,"Account not found", HttpStatus.NOT_FOUND);
		}
		ResponseEntity<Map<String, Account>> entity = new ResponseEntity<>(responseMap, HttpStatus.OK);
		return entity;		
	}
	
	@RequestMapping(method = RequestMethod.POST, value ="/customer/{id}/account")
	public ResponseEntity<Map<String, Long>>  addAccount(@Valid @RequestBody AccountRequest accountRequest, @PathVariable Long id) {
		logger.info("Entering addAccount");
		return accountService.addAccount(id, accountRequest);
	
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/customer/{id}/account/{acId}")
	public ResponseEntity<Map<String, String>>  deleteAccount(@PathVariable Long id, @PathVariable Long acId) {
		logger.info("Entering deleteAccount");
		accountService.deleteAccount(id,acId);
		Map<String,String> msg = new HashMap<>();
		msg.put("message", "Deleted account successfully");
		return new ResponseEntity<>(msg,HttpStatus.OK);
	}
	
		
	@RequestMapping("/customer/{id}/accounts")
	public List<Account> getAccountsByCustomerId(@PathVariable Long id){
		logger.info("Entering getAccountsByCustomerId");
		return accountService.getAccountsByCustomerId(id);
	}
	
	@RequestMapping(method = RequestMethod.PATCH, value = "/customer/{id}/closeAccount/{acId}")
	public ResponseEntity<Map<String, String>> closeAccount(@PathVariable Long id, @PathVariable Long acId) {
		logger.info("Entering closeAccount");
		accountService.closeAccount(id, acId);
		Map<String,String> msg = new HashMap<>();
		msg.put("message", "Closed account successfully");
		return new ResponseEntity<>(msg,HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.PATCH, value = "/customer/{id}/closeAccounts")	
	public ResponseEntity<Map<String, String>> closeCustomerAccounts(@PathVariable Long id) {
		logger.info("Entering closeCustomerAccounts");
		accountService.closeCustomerAccounts(id);
		Map<String,String> msg = new HashMap<>();
		msg.put("message", "Closed accounts successfully");
		return new ResponseEntity<>(msg,HttpStatus.OK);
		
	}
	
}

