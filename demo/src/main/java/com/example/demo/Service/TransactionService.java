package com.example.demo.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.Dto.TransactionDto;
import com.example.demo.Entity.Account;
import com.example.demo.Entity.MyUser;
import com.example.demo.Entity.Transaction;
import com.example.demo.Exception.BankAccNotFoundException;
import com.example.demo.Exception.MyUserNotFoundException;
import com.example.demo.Exception.TransactionNotFoundException;
import com.example.demo.Repository.AccountRepository;
import com.example.demo.Repository.MyUserRepository;
import com.example.demo.Repository.TransactionRepository;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final MyUserRepository myUserRepository;


    private final String transactionNotFound = "Transaction Not Found";
    private final String accountNotFound = "Account Not Found";
    private final String myUserNotFound = "User Not Found";

    public TransactionService(TransactionRepository transactionRepository, 
                                AccountRepository accountRepository, 
                                MyUserRepository myUserRepository) {
        this.transactionRepository = transactionRepository;
        this.myUserRepository = myUserRepository;
        this.accountRepository = accountRepository;
    }

    public Transaction getTransactionByTransactionId(Transaction transaction) {
        return transactionRepository.findByTransactionId(transaction.getTransactionId())
            .orElseThrow(() -> new TransactionNotFoundException(transactionNotFound));
    }

    public void registerTransaction(String email, TransactionDto transactionDto) {
        Transaction transaction = new Transaction(
            generateTransactionId(), 
            transactionDto.getAmount(), 
            transactionDto.getTransactionType(), 
            LocalDate.now(), 
            transactionDto.getAccount()
        );

        Account account = accountRepository.findByAccountId(transactionDto.getAccount().getAccountId())
            .orElseThrow(() -> new BankAccNotFoundException(accountNotFound));

        account.getListOfTransactions().add(transaction);

        transactionRepository.save(transaction);
        System.out.println(transaction);
    }

    public List<Transaction> getAllTransactionByEmail(String email) {
        MyUser myUser = myUserRepository.findMyUserByEmail(email)
            .orElseThrow(() -> new MyUserNotFoundException(myUserNotFound));

        List<Transaction> result = new ArrayList<Transaction>();

        myUser.getListOfAccounts().forEach(account -> {
            account.getListOfTransactions().forEach(transaction -> {
                result.add(transaction);
            });
        });

        return result;
    }

    public String generateTransactionId() {
        String transactionNumber = UUID
        .randomUUID()
        .toString()
        .replace("-", "")
        .substring(0, 8);

        return transactionNumber;
    }
}
