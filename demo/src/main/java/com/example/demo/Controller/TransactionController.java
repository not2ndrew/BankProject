package com.example.demo.Controller;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.Entity.Transaction;
import com.example.demo.Service.TransactionService;


@Controller
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/transaction")
    public String showTransactionPage(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Transaction> transactionList = transactionService.getAllTransactionByEmail(email);
        model.addAttribute("transactionList", transactionList);
        
        return "main/transaction";
    }
    
}
