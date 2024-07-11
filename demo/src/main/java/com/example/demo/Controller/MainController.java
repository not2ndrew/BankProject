package com.example.demo.Controller;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.Dto.TransferRequest;
import com.example.demo.Entity.Account;
import com.example.demo.Entity.MyUser;
import com.example.demo.Entity.Transaction;
import com.example.demo.Service.AccountService;


@Controller
public class MainController {
    private AccountService accountService;

    public MainController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/main")
    public String showMainPage(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Account> accountList = accountService.getAllAccountsByEmail(email);
        model.addAttribute("accountList", accountList);

        double totalBalance = accountService.getTotalBalanceByEmail(email);
        model.addAttribute("totalBalance", totalBalance);

        model.addAttribute("transaction", new Transaction());

        model.addAttribute("transferRequest", new TransferRequest());

        return "main/main";
    }

    @PostMapping("/main")
    public String postMainPage(@ModelAttribute MyUser myUser) {
        return "redirect:/main";
    }
}
