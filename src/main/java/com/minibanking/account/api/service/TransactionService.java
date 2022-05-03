package com.minibanking.account.api.service;

import com.minibanking.account.api.entity.*;
import com.minibanking.account.api.exception.AccountException;
import com.minibanking.account.api.model.DepositRequest;
import com.minibanking.account.api.model.FundTransferRequest;
import com.minibanking.account.api.model.WithdrawRequest;
import com.minibanking.account.api.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;

@Service
public class TransactionService {

    Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    AccountService accountService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    DepositRepository depositRepository;

    @Autowired
    WithdrawRepository withdrawRepository;

    @Autowired
    FundTransferRepository fundTransferRepository;


    public ResponseEntity<Map<String,List<Transaction>>> getAllTransactionsByDate(LocalDate date){
        Map<String,List<Transaction>> responseMap = new HashMap<>();
        List<Transaction> txnList = transactionRepository.findAllTransactionsByDate(date);
        responseMap.put("TransactionList", txnList);
        return new ResponseEntity<Map<String, List<Transaction>>>(responseMap,HttpStatus.OK);

        }

    public ResponseEntity<Map<String, Transaction>> getTransactionById(Long txnId){
        Optional<Transaction> transaction = transactionRepository.findById(txnId);
        Map<String, Transaction> transactionMap = new HashMap<>();
        if (transaction.isPresent())
            transactionMap.put("Transaction", transaction.get());
        else{
            throw new AccountException(4050, "Transaction not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Map<String,Transaction>> (transactionMap,HttpStatus.OK);
    }
    @Transactional
    public ResponseEntity<Map<String,String>> fundTransfer(FundTransferRequest fundTransferRequest) {

        logger.info("Entering fundTransfer");
        try {
            List<Account> accountList = accountService.getAccountsByCustomerId(fundTransferRequest.getFromCustomerId());
            logger.info("Entering 1st in try");
            Optional<Account> toAccount = accountService.getAccountById(fundTransferRequest.getToAccountNo());
            System.out.println("the accountlist size is :" + accountList.size());

            logger.info("in try block");
            Account toAcc = toAccount.get();
            System.out.println("----------" + accountList.size());

            Transaction transaction = new Transaction();
            FundTransfer fundTransfer = new FundTransfer(fundTransferRequest);


            if (accountList.size() != 0 && accountList.stream().anyMatch(account -> Objects.equals(account.getAcNo(), fundTransferRequest.getFromAccountNo()))) {
                logger.info("Entering first if");
                if (toAcc.getAcNo().equals(fundTransferRequest.getToAccountNo())) {
                    logger.info("Entering entering 2nd if");
                    Optional<Account> fromAccount = accountService.getAccountById(fundTransferRequest.getFromAccountNo());
                    Account fromAcc = fromAccount.get();
                    Long fromAccountNewBalance = fromAcc.getBalance() - fundTransferRequest.getAmount();
                    Long toAccountNewBalance = toAcc.getBalance() + fundTransferRequest.getAmount();

                    transaction.setTransactionType("FundTransfer");
                    transaction.setBalance(fromAccountNewBalance);
                    transaction.setAmount(fundTransferRequest.getAmount());
                    transactionRepository.save(transaction);

                    fromAcc.setBalance(fromAccountNewBalance);
                    toAcc.setBalance(toAccountNewBalance);
                    accountRepository.save(fromAcc);
                    accountRepository.save(toAcc);

                    fundTransfer.setTransaction(transaction);
                    fundTransfer.setAmount(fundTransferRequest.getAmount());
                    fundTransferRepository.save(fundTransfer);
                    Map<String,String> responseMap = new HashMap<>();
                    responseMap.put("message", "Transferred fund successfully");
                    return new ResponseEntity<>(responseMap,HttpStatus.OK);

                } else {
                    throw new AccountException(3010, "The To_Account doesn't exist", HttpStatus.BAD_REQUEST);
                }
            } else {
                throw new AccountException(3000, "Account does not belong to this customer", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            throw new AccountException(3020, "Wrong Account No(s) or Customer id ", HttpStatus.BAD_REQUEST);

            }
    }
    @Transactional
    public ResponseEntity<Map<String,String>> deposit(DepositRequest depositRequest) {
           logger.info("Entering deposit");
        try {
            Optional<Account> toaccount = accountService.getAccountById(depositRequest.getToAccountNo());
            Account toAcc = toaccount.get();
            Transaction transaction = new Transaction();
            Deposit deposit = new Deposit(depositRequest);
            deposit.setTransactionRefNo(transaction.getTransactionRefNo());
            if (toAcc.getAcNo().equals(depositRequest.getToAccountNo())) {
                Long newBalance = toAcc.getBalance() + depositRequest.getAmount();
                transaction.setTransactionType("Cash deposit");
                transaction.setBalance(newBalance);
                transaction.setAmount(depositRequest.getAmount());
                transactionRepository.save(transaction);
                deposit.setTransaction(transaction);
                deposit.setBalance(newBalance);
                toAcc.setBalance(newBalance);
                accountRepository.save(toAcc);
                depositRepository.save(deposit);
                Map<String,String> responseMap = new HashMap<>();
                responseMap.put("message","Amount deposited successfully");
                return new ResponseEntity<>(responseMap,HttpStatus.OK);

                } else {
                throw new AccountException(3010, "The To_Account doesn't exist", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e){
            throw new AccountException(3020,"Invalid account number", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public ResponseEntity<Map<String,String>> withdraw(WithdrawRequest withdrawRequest) {


        logger.info("Entering withdraw");
        List<Account> accountList = accountService.getAccountsByCustomerId(withdrawRequest.getFromCustomerId());
        Optional<Account> fromAccount = accountService.getAccountById(withdrawRequest.getFromAccountNo());
        Account acc = fromAccount.get();
        Transaction transaction = new Transaction();
        Withdraw withdraw = new Withdraw(withdrawRequest);
        if (accountList.size() !=0 && accountList.stream().anyMatch(account -> Objects.equals(account.getAcNo(),withdrawRequest.getFromAccountNo()))){
             if(withdrawRequest.getAmount() < acc.getBalance()){
                Long newBalance = acc.getBalance() - withdrawRequest.getAmount();
                transaction.setTransactionType("Withdrawl");
                transaction.setBalance(newBalance);
                transaction.setAmount(-withdrawRequest.getAmount());
                transactionRepository.save(transaction);
                withdraw.setTransaction(transaction);
                withdraw.setBalance(newBalance);
                accountRepository.save(acc);
                withdrawRepository.save(withdraw);
                Map<String,String> responseMap = new HashMap<>();
                responseMap.put("message", "Withdrawl done successfully");
                return new ResponseEntity<>(responseMap, HttpStatus.OK);
             } else {
                throw new AccountException(6010,"Not enough money to withdraw", HttpStatus.BAD_REQUEST);
            }
        } else {
            throw new AccountException(3000, "Account does not belong to this customer", HttpStatus.BAD_REQUEST);
        }

    }
}
