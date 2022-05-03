package com.minibanking.account.api.repository;

import com.minibanking.account.api.entity.FundTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FundTransferRepository extends JpaRepository<FundTransfer, Long> {
}
