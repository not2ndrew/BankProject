package com.example.demo.Entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Data
/* TO AVOID STACKOVERFLOW ERROR */
@ToString(exclude = "account")
public class Transaction {
    @Id
    @SequenceGenerator(name = "transaction_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "transaction_seq")
    private Long id;

    private String transactionId;
    private double amount;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private LocalDate createdAt;

    @ManyToOne
    private Account account;

    public Transaction(String transactionId, double amount, TransactionType transactionType, LocalDate createdAt, Account account) {
        this.transactionId = transactionId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.createdAt = createdAt;
        this.account = account;
    }
}




