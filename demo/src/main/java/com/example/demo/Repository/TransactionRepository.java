package com.example.demo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>{
    Optional<Transaction> findByTransactionId(String accountId);
}
