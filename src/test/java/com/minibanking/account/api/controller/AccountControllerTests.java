package com.minibanking.account.api.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.minibanking.account.api.controller.AccountController;
import com.minibanking.account.api.service.AccountService;

@WebMvcTest(AccountController.class)
public class AccountControllerTests {
	
	@MockBean
	AccountService accountService;
	
	@Autowired
	MockMvc mockMvc;

//	@Test
//	public void testAddAccount() {
//		AccountRequest accountRequest = new AccountRequest("savings","123456", "sam@gmail.com");
//		Long id = 1002022001L;
//		
//		
//	}
	
	@Test
	public void testGetAccountById() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/account/{acId}")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON)
				);
		
	}
}
