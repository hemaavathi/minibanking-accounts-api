package com.minibanking.account.api.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.minibanking.account.api.entity.Account;
import com.minibanking.account.api.service.AccountService;

@WebMvcTest(AccountController.class)
public class AccountControllerTests {
	
	@MockBean
	AccountService accountService;
	
	@Autowired
	MockMvc mockMvc;

	
	@Test
	public void testGetAccountById() throws Exception {
				
		Account account = new Account();
		account.setAcNo(2002022001L);

		when(accountService.getAccountById(2002022001L)).thenReturn(Optional.of(account));
		
		mockMvc.perform(MockMvcRequestBuilders.get("/account/4002022002L")
				.contentType(MediaType.APPLICATION_JSON))
				
		.andExpect(status().isBadRequest());
	
	}
	
	
}
