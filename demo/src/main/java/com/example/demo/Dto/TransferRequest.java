package com.example.demo.Dto;

import com.example.demo.Entity.Account;
import com.example.demo.Entity.TransactionType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "accountSource")
public class TransferRequest {
    /* This is the same as transactionId */
    private String transactionId;
    private Account accountSource;
    private Account accountTarget;

    private double amount;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
}
