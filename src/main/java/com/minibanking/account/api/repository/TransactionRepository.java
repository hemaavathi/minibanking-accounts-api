package com.minibanking.account.api.repository;

import com.minibanking.account.api.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllTransactionsByDate(LocalDate date);


}
