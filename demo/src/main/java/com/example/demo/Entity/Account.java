package com.example.demo.Entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
/* TO AVOID STACKOVERFLOW ERROR */
@ToString(exclude = "listOfTransactions")
public class Account {
    @Id
    @SequenceGenerator(name = "account_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "account_seq")
    private Long id;

    private String accountId;
    private String name;
    private String type;
    private double balance;

    private LocalDate createdAt;

    @ManyToOne
    private MyUser myUser;

    @OneToMany
    private List<Transaction> listOfTransactions;

    public Account(String accountId, String name, String type, double balance, LocalDate createdAt, List<Transaction> listOfTransactions) {
        this.accountId = accountId;
        this.name = name;
        this.type = type;
        this.balance = balance;
        this.createdAt = createdAt;
        this.listOfTransactions = listOfTransactions;
    }
}
