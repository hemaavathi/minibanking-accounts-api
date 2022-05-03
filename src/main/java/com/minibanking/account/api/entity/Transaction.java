package com.minibanking.account.api.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.minibanking.account.api.model.FundTransferRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_generator")
    @SequenceGenerator(name = "payment_generator", sequenceName = "transaction_No", allocationSize = 1)
    @Column(name="txn_ref_no", updatable = false, nullable = false)
    private Long transactionRefNo;
    private Long amount;
    private Long balance;
    @Column(name="txn_type")
    private String transactionType;
    private LocalDate date = LocalDate.now();
    @JsonManagedReference
    @OneToOne(mappedBy = "transaction", cascade = CascadeType.ALL)
    @JoinColumn
    private Deposit deposit;
    @JsonManagedReference
    @OneToOne(mappedBy = "transaction", cascade = CascadeType.ALL)
    @JoinColumn
    private FundTransfer fundTransfer;
    @JsonManagedReference
    @OneToOne(mappedBy = "transaction", cascade = CascadeType.ALL)
    @JoinColumn
    private Withdraw withdraw;


    }

