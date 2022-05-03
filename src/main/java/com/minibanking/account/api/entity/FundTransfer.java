package com.minibanking.account.api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.minibanking.account.api.model.FundTransferRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="fundtransfer")
public class FundTransfer {
    @Id
    @Column(name="txn_ref_no" )
    private Long transactionRefNo;
    @Column(name="customer_id")
    private Long customerId;
    @Column(name="to_acc_no")
    private Long toAccountNo;
    @Column(name="from_acc_no")
    private Long fromAccountNo;
    private Long amount;
    @Column(name="to_acc_name")
    private String toAccountName;
    private LocalDate date = LocalDate.now();
    @JsonBackReference
    @OneToOne
    @MapsId
    @JoinColumn(name = "txn_ref_no")
    private Transaction transaction;

    public FundTransfer(FundTransferRequest fundTransferRequest){
        this.fromAccountNo= fundTransferRequest.getFromAccountNo();
        this.toAccountNo= fundTransferRequest.getToAccountNo();
        this.customerId = fundTransferRequest.getFromCustomerId();
        this.toAccountName = fundTransferRequest.getToAccountName();
    }
}
