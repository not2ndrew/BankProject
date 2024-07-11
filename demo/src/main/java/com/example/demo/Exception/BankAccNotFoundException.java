package com.example.demo.Exception;

public class BankAccNotFoundException extends RuntimeException {
    public BankAccNotFoundException(String message) {
        super(message);
    }
}
