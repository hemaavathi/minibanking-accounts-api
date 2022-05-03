package com.minibanking.account.api.repository;

import com.minibanking.account.api.entity.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepositRepository extends JpaRepository<Deposit, Long> {
}
