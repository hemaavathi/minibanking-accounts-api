package com.minibanking.account.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.minibanking.account.api.entity.Account;
import com.minibanking.account.api.exception.AccountException;
import com.minibanking.account.api.model.AccountRequest;
import com.minibanking.account.api.repository.AccountRepository;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AccountServiceTests {
	
	@InjectMocks
	AccountService accountService;
	
	@Mock
	AccountRepository accountRepository;
	
	@Mock
	RestTemplate restTemplate;
	
	@Test
	public void addAccount() {
		
		MockitoAnnotations.initMocks(this);
		
		AccountRequest req = AccountRequest.builder()
				.acType("Savings")
				.bsb("123456")
				.payId("123456").build();
		
		// mock Account acc = accountRepository.save(account);
		Account account = new Account(req);
		account.setCustomerId(123456L);
		account.setStatus("ACTIVE");
		account.setBalance(0L);
		Account respAccount = new Account();
		respAccount.setAcNo(987654321L);
		when(accountRepository.save(account)).thenReturn(respAccount);		
		
		
		// mock service call to get customer details
		Map<String,Long> param = new HashMap<>();
		param.put("id", 123456L);	
		//ResponseEntity<String> body = restTemplate.getForEntity("http://localhost:8080/customer/{id}", String.class, param);
		String body = "{\"customer\": {\"id\": 123456,\"name\": \"Bob\",\"dob\": \"1986-08-06\",\"address\": \"Australia\",\"mobile\": \"1234567890\",\"email\": \"b@gmail.com\",\"kycType\": \"Passport\",\"kycNo\": \"098385490\"}}";
		ResponseEntity<String> mockCustomerResponse = new ResponseEntity<>(body, HttpStatus.OK);
		accountService.setHostName("localhost");
		accountService.setPort("8080");

		when(restTemplate.getForEntity("http://localhost:8080/customer/{id}", String.class, param)).thenReturn(mockCustomerResponse);
		
		
		// prepare inputs to test method
		Long customerId = 123456L;
		

		// actual method to be tested
		ResponseEntity<Map<String, Long>> response = accountService.addAccount(customerId, req);
		
		// test my expectstaions
		// service return 201
		// json payload
		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
		Map<String, Long> responseMap = response.getBody();
		Long responseAcNo = responseMap.get("acNo");
		Assertions.assertEquals(987654321L, responseAcNo);
				
	}
	
	@Test
	public void deleteAccount() {
		
		Account account = Account.builder()
				.acNo(2002022001L)
				.acType("Savings")
				.balance(0L)
				.bsb("23487998")
				.customerId(1002022001L)
				.payId("20748384")
				.status("ACTIVE").build();
		when (accountRepository.findById(2002022001L)).thenReturn(Optional.of(account));		
		
		accountService.deleteAccount(1002022001L, 2002022001L);
		
		verify(accountRepository).deleteById(2002022001L);
			
	}
	@Test
	public void deleteInvalidAccount() {
		
		when (accountRepository.findById(2002022002L)).thenReturn(Optional.empty());		
		
		assertThrows(AccountException.class, () -> {
			accountService.deleteAccount(1002022001L, 2002022002L);
			});
		AccountException response = null;
		try {
			accountService.deleteAccount(1002022001L, 2002022002L);
		} catch (AccountException e) {
			response = e;
		  }
		assertEquals("Account does not exist",response.getMessage());
	}
	
	
	@Test
	public void closeAccount() {
		Account account = Account.builder()
				.acNo(2002022001L)
				.acType("Savings")
				.balance(0L)
				.bsb("23487998")
				.customerId(1002022001L)
				.payId("20748384")
				.status("ACTIVE").build();
		when (accountRepository.findById(2002022001L)).thenReturn(Optional.of(account));		
		
		//mock service call get customer details
		String body = "{\"customer\": {\"id\": 1002022001,\"name\": \"Bob\",\"dob\": \"1986-08-06\",\"address\": \"Australia\",\"mobile\": \"1234567890\",\"email\": \"b@gmail.com\",\"kycType\": \"Passport\",\"kycNo\": \"098385490\"}}";
		ResponseEntity<String> mockCustomerResponse = new ResponseEntity<>(body, HttpStatus.OK);
		Map<String,Long> param = new HashMap<>();
		param.put("id", 1002022001L);
		accountService.setHostName("localhost");
		accountService.setPort("8080");		
		when(restTemplate.getForEntity("http://localhost:8080/customer/{id}", String.class, param)).thenReturn(mockCustomerResponse);

		accountService.closeAccount(1002022001L, 2002022001L);
		
		verify(accountRepository).closeAccount(2002022001L);
		
	}
	
}
