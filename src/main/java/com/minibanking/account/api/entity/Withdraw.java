package com.minibanking.account.api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.minibanking.account.api.model.WithdrawRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Withdraw {
    @Id
    @Column(name="txn_ref_no")
    private Long transactionRefNo;
    @Column(name="customer_id")
    private Long customerId;
    @Column(name="from_acc_no")
    private Long fromAccountNo;
    private Long amount;
    private Long balance;
    private LocalDate date = LocalDate.now();
    @JsonBackReference
    @OneToOne
    @MapsId
    @JoinColumn(name = "txn_ref_no")
    private Transaction transaction;

    public Withdraw(WithdrawRequest withdrawRequest){
        this.fromAccountNo= withdrawRequest.getFromAccountNo();
        this.customerId = withdrawRequest.getFromCustomerId();
        this.amount = withdrawRequest.getAmount();
    }
}
