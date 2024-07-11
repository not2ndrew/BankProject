package com.example.demo.Mapper;

import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.example.demo.Dto.AccountRequest;
import com.example.demo.Entity.Account;

@Service
public class AccountReqMapper implements Function<Account, AccountRequest> {

    @Override
    public AccountRequest apply(Account account) {
        return new AccountRequest(
            account.getAccountId(), 
            account.getName(), 
            account.getType(), 
            account.getBalance()
        );
    }
}
