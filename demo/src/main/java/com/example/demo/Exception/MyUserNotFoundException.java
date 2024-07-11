package com.example.demo.Exception;

public class MyUserNotFoundException extends RuntimeException {
    public MyUserNotFoundException(String message) {
        super(message);
    }
}
