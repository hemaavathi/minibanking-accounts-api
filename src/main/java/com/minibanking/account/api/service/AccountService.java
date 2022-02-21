package com.minibanking.account.api.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minibanking.account.api.entity.Account;
import com.minibanking.account.api.exception.AccountException;
import com.minibanking.account.api.model.AccountRequest;
import com.minibanking.account.api.model.CustomerApiResponse;
import com.minibanking.account.api.repository.AccountRepository;

@Service
public class AccountService {
	
	Logger logger = LoggerFactory.getLogger(AccountService.class);
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private RestTemplate restTemplate;

	public List<Account> getAllAccounts(){
		logger.info("Entering getAllAccounts");
		return accountRepository.findAll();
	}
	
	public Optional<Account> getAccountById(Long id){
		logger.info("Entering getAccountById");
		return accountRepository.findById(id);
	}
	
	public ResponseEntity<Map<String, Long>> addAccount(Long id, AccountRequest accountRequest) {
		logger.info("Entering addAccount");
		CustomerApiResponse c = getCustomerDetails(id);
		if (c != null && c.getId() != null && c.getId().equals(id)) {
			Account account = new Account(accountRequest);
			account.setCustomerId(c.getId());
			account.setStatus("ACTIVE");
			account.setBalance(0L);
			Account acc = accountRepository.save(account);
			Long acNo = acc.getAcNo();
			Map<String, Long> responseMap = new HashMap<>();
			responseMap.put("acNo", acNo);			
			ResponseEntity<Map<String, Long>> entity = new ResponseEntity<>(responseMap, HttpStatus.CREATED);
			return entity;
			
		} else {
			
			throw new AccountException(2000, "Customer not found", HttpStatus.NOT_FOUND);
						
		} 
			
	}
	
	public void deleteAccount(Long id, Long acId) {
		logger.info("Entering deleteAccount");
		Account account = null;
		Optional<Account> acc = accountRepository.findById(acId);
		if(acc.isPresent()) {
			account = acc.get();
		} else {
			throw new AccountException(3010, "Account does not exist", HttpStatus.BAD_REQUEST);
		}
		if (account.getCustomerId().equals(id)) {
			accountRepository.deleteById(acId);
		} else	{
			throw new AccountException(3000, "Account does not belong to this customer", HttpStatus.BAD_REQUEST);
	   	}
		
	}
	
	public CustomerApiResponse getCustomerDetails(Long id) {
		logger.info("Entering getCustomerDetails");
		Map<String,Long> param = new HashMap<>();
		param.put("id", id);

		// We need to map the response object, so creating an instance of ObjectMapper.
		ObjectMapper mapper = new ObjectMapper();

		// customer response is in this form. So creating an instance of it.
		Map<String, CustomerApiResponse> response = null;

		// I need to hold final customer object. so creating an instance of it.
		CustomerApiResponse customerApiResponse = null;

		try {
			// Calling the service and getting the response in the form of string.
			ResponseEntity<String> body = restTemplate.getForEntity("http://localhost:8080/customer/{id}", String.class, param);
			if (body.hasBody()) {
				// now deserialise the json string into java object using mapper instance
				response = mapper.readValue(body.getBody(), new TypeReference<Map<String, CustomerApiResponse>>(){});
				// After deserialisation, we got the Map<String, Customer>. So we are accessing .get("customer") to get the final customer entity
				customerApiResponse = response.get("customer");
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return customerApiResponse;
	}
	
	public List<Account> getAccountsByCustomerId(Long id){
		
		return accountRepository.findAccountsByCustomerId(id);
	}
	
	@Transactional
	public void closeAccount(Long id, Long acId) {
		logger.info("Entering closeAccount");
		Optional<Account> account = accountRepository.findById(acId);
		if(account.isPresent()) {
			Account acc = account.get();
			if (acc.getCustomerId().equals(id)) {
				accountRepository.closeAccount(acId);
			} else {
				throw new AccountException(3000, "Account does not belong to this customer", HttpStatus.BAD_REQUEST);
			}			
		} else {
			throw new AccountException(3010, "Account does not exist", HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@Transactional
	public void closeCustomerAccounts(Long id) {
		logger.info("Entering closeCustomerAccounts");
		CustomerApiResponse customer = getCustomerDetails(id);
		if(customer != null && customer.getId().equals(id)) {
			accountRepository.closeCustomerAccounts(id);
		} else {
			throw new AccountException(2000, "Customer not found", HttpStatus.NOT_FOUND);
		}
		
	}
}
