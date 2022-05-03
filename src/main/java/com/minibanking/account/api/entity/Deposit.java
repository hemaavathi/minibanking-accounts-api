package com.minibanking.account.api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.minibanking.account.api.model.DepositRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Deposit {
    @Id
    @Column(name="txn_ref_no")
    private Long transactionRefNo;
    @Column(name="to_acc_no")
    private Long toAccountNo;
    private Long amount;
    private Long balance;
    @Column(name="to_acc_name")
    private String toAccountName;
    private LocalDate date = LocalDate.now();
    @JsonBackReference
    @OneToOne
    @MapsId
    @JoinColumn(name = "txn_ref_no")
    private Transaction transaction;

    public Deposit(DepositRequest depositRequest){
        this.toAccountNo = depositRequest.getToAccountNo();
        this.toAccountName = depositRequest.getToAccountName();
        this.amount = depositRequest.getAmount();
    }
}
