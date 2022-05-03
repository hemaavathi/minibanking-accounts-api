package com.minibanking.account.api.repository;

import com.minibanking.account.api.entity.Withdraw;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WithdrawRepository extends JpaRepository<Withdraw,Long> {
}
