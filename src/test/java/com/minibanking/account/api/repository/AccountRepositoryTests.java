package com.minibanking.account.api.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.minibanking.account.api.entity.Account;

@DataJpaTest
public class AccountRepositoryTests {

	@Autowired
	AccountRepository repo;
	
	@Autowired
	EntityManager entity;
	
	@Test
	public void testGetAccountsByCustomerId() {
		Account account1 = Account.builder()
				.acType("Savings")
				.customerId(1002022001L)
				.bsb("203948948")
				.payId("23984790")
				.balance(0L)
				.build();
		
		Account account2 = Account.builder()
				.acType("Domestic")
				.customerId(1002022001L)
				.bsb("203948948")
				.payId("23984790")
				.balance(0L)
				.build();
		
		entity.persist(account1);
		entity.persist(account2);
		entity.flush();
		entity.close();
		
		List<Account> acctList = repo.findAccountsByCustomerId(1002022001L);
		
		assertNotNull(acctList);
		assertEquals(2,acctList.size());
	}
	
	@Test
	public void testCloseAccount() {
		Account account1 = Account.builder()
				.acType("Savings")
				.customerId(1002022001L)
				.bsb("203948948")
				.payId("23984790")
				.status("ACTIVE")
				.balance(0L)
				.build();
		
		Account account2 = Account.builder()
				.acType("Domestic")
				.customerId(1002022001L)
				.bsb("203948948")
				.payId("23984790")
				.status("ACTIVE")
				.balance(0L)
				.build();
		
		entity.persist(account1);
		entity.persist(account2);
		entity.flush();
		entity.close();
		
		System.out.println(account2.getStatus());
		repo.closeAccount(account2.getAcNo());
		
		
		Optional<Account> account = repo.findById(account2.getAcNo());
		System.out.println(account2.getStatus());
		System.out.println(account.get().getStatus());
		
		assertEquals("INACTIVE",account.get().getStatus());
	}
}
