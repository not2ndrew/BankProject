package com.example.demo.Dto;

import com.example.demo.Entity.Account;
import com.example.demo.Entity.TransactionType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    private String transactionId;
    private double amount;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private Account account;
}
