package com.example.demo.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.Dto.AccountRequest;
import com.example.demo.Entity.Account;
import com.example.demo.Entity.MyUser;
import com.example.demo.Entity.Transaction;
import com.example.demo.Exception.BankAccNotFoundException;
import com.example.demo.Exception.MyUserNotFoundException;
import com.example.demo.Exception.PaymentException;
import com.example.demo.Mapper.AccountReqMapper;
import com.example.demo.Repository.AccountRepository;
import com.example.demo.Repository.MyUserRepository;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final MyUserRepository myUserRepository;

    private final AccountReqMapper accountReqMapper;

    private final String accountNotFound = "Account Not Found";
    private final String myUserNotFound = "User Not Found";

    public AccountService(AccountRepository accountRepository, MyUserRepository myUserRepository, 
                          AccountReqMapper accountReqMapper) {
        this.accountRepository = accountRepository;
        this.myUserRepository = myUserRepository;
        this.accountReqMapper = accountReqMapper;
    }

    public Account registerAccount(String email, AccountRequest accountRequest) {
        Account account = new Account(
            generateAccountId(), 
            accountRequest.getName(), 
            accountRequest.getType(), 
            0.0, 
            LocalDate.now(), 
            new ArrayList<Transaction>()
        );

        
        MyUser myUser = myUserRepository.findMyUserByEmail(email)
            .orElseThrow(() -> new MyUserNotFoundException(myUserNotFound));

        /* THIS CAUSES A STACKOVERFLOW ERROR */
        /* Make sure to use Lombok's ToString(exclude) feature in the Entity classes */
        /* OR override ToString and get the Id of the variable's list. */
        myUser.getListOfAccounts().add(account);

        return accountRepository.save(account);
    }

    public String generateAccountId() {
        return UUID
            .randomUUID()
            .toString()
            .replace("-", "")
            .substring(0, 6);
    }

    public List<Account> getAllAccountsGlobally() {
        return accountRepository.findAll();
    }

    public List<Account> getAllAccountsByEmail(String email) {
        MyUser myUser = myUserRepository.findMyUserByEmail(email)
            .orElseThrow(() -> new MyUserNotFoundException(myUserNotFound));

        return myUser.getListOfAccounts();
    }

    public AccountRequest getAccountByAccountId(AccountRequest accountRequest) {
        String accountId = accountRequest.getAccountId();
        return accountRepository.findByAccountId(accountId)
            .map(accountReqMapper)
            .orElseThrow(() -> new BankAccNotFoundException(accountNotFound));
    }

    public double getTotalBalanceByEmail(String email) {
        List<Account> listOfAccounts = getAllAccountsByEmail(email);

        double totalBalance = 0.0;

        for (Account account : listOfAccounts) {
            totalBalance += account.getBalance();
        }

        return totalBalance;
    }

    public String generateTransactionId() {
        String transactionNumber = UUID
        .randomUUID()
        .toString()
        .replace("-", "")
        .substring(0, 8);

        return transactionNumber;
    }

    public void deposit(Account account, double amount) {
        String accountId = account.getAccountId();

        Account request = accountRepository.findByAccountId(accountId)
            .orElseThrow(() -> new BankAccNotFoundException(accountNotFound));

        if (amount <= 0) {
            throw new PaymentException("Amount cannot be less than 0");
        }

        double newBalance = request.getBalance() + amount;
        request.setBalance(newBalance);
    }

    public void withdraw(Account account, double amount) {
        String accountId = account.getAccountId();
        
        Account request = accountRepository.findByAccountId(accountId)
            .orElseThrow(() -> new BankAccNotFoundException(accountNotFound));

        if (amount <= 0) {
            throw new PaymentException("Amount cannot be less than 0");
        }

        if (amount > request.getBalance()) {
            throw new PaymentException("Amount requested is greater than current account balance");
        }

        double newBalance = request.getBalance() - amount;
        request.setBalance(newBalance);
    }

    public void transfer(Account accountSource, Account accountTarget, double amount) {
        String accountIdSource = accountSource.getAccountId();
        String accountIdTarget = accountTarget.getAccountId();

        Account requestSource = accountRepository.findByAccountId(accountIdSource)
            .orElseThrow(() -> new BankAccNotFoundException(accountNotFound));

        Account requestTarget = accountRepository.findByAccountId(accountIdTarget)
            .orElseThrow(() -> new BankAccNotFoundException(accountNotFound));

        this.withdraw(requestSource, amount);
        this.deposit(requestTarget, amount);
    }
}
