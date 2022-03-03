package com.minibanking.account.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.minibanking.account.api.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

	List<Account> findAccountsByCustomerId(Long customerId);
	
	@Modifying(clearAutomatically = true)
	@Query("Update Account set status = 'INACTIVE' where acNo = :acId")
	void closeAccount(@Param("acId") Long accountId);
	
	@Modifying
	@Query("Update Account set status = 'INACTIVE' where customerId = :custId")
	void closeCustomerAccounts(@Param ("custId") Long id);
}
