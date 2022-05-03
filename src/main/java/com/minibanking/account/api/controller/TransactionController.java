package com.minibanking.account.api.controller;

import com.minibanking.account.api.entity.Transaction;
import com.minibanking.account.api.model.DepositRequest;
import com.minibanking.account.api.model.FundTransferRequest;
import com.minibanking.account.api.model.WithdrawRequest;
import com.minibanking.account.api.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;


    @GetMapping("/accounts/transactions/{date}")
    public ResponseEntity<Map<String,List<Transaction>>> getAllTransactionsByDate(@PathVariable ("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date){

        return transactionService.getAllTransactionsByDate(date);
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<Map<String,Transaction>> getTransactionById(@PathVariable ("id") Long txnId){
        return transactionService.getTransactionById(txnId);
    }

    @PutMapping("/accounts/fundTransfer")
    public ResponseEntity<Map<String,String>> fundTransfer(@RequestBody FundTransferRequest fundTransferRequest){
       return transactionService.fundTransfer(fundTransferRequest);
    }

    @PutMapping("/accounts/deposit")
    public ResponseEntity<Map<String,String>> deposit(@RequestBody DepositRequest depositRequest){
        return transactionService.deposit(depositRequest);
    }

    @PutMapping("/accounts/withdraw")
    public ResponseEntity<Map<String,String>> withdraw(@RequestBody WithdrawRequest withdrawRequest){
       return transactionService.withdraw(withdrawRequest);
    }
}
