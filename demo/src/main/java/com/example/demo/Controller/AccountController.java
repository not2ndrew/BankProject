package com.example.demo.Controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.Dto.AccountRequest;
import com.example.demo.Dto.TransactionDto;
import com.example.demo.Dto.TransferRequest;
import com.example.demo.Entity.Account;
import com.example.demo.Entity.TransactionType;
import com.example.demo.Service.AccountService;
import com.example.demo.Service.TransactionService;

@Controller
public class AccountController {
    private final AccountService accountService;
    private final TransactionService transactionService;

    private final String emptyErrorMsg = "Error: Name is Empty";
    private final String notSelectedErrorMsg = "Error: Account is not selected";
    private final String sameSelectedErrorMsg = "Error: Account transferred to itself";
    private final String insufficientAmount = "Error: Amount cannot be less than 0";

    public AccountController(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @GetMapping("/account")
    public String showAccountPage(Model model) {
        model.addAttribute("account", new Account());

        return "main/account";
    }

    @PostMapping("/account")
    public String createAccount(@ModelAttribute AccountRequest accountRequest, RedirectAttributes redirectAttributes) {
        // SecurityContextHolder
        // https:/www.youtube.com/watch?v=MlKT8IOTfcw
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        if (accountRequest.getName().isBlank()) {
            redirectAttributes.addFlashAttribute("error", emptyErrorMsg);
            System.out.println(emptyErrorMsg);
            return "redirect:/main";
        }

        Account test = accountService.registerAccount(email, accountRequest);
        System.out.println(test);

        return "redirect:/main";
    }

        // Deposit, Withdraw, Transfer methods all use POST from HTML
    @PostMapping("/depositForm")
    public String depositForm(@ModelAttribute TransactionDto transactionDto, RedirectAttributes redirectAttributes) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        if (transactionDto.getAccount() == null) {
            redirectAttributes.addFlashAttribute("error", notSelectedErrorMsg);
            return "redirect:/main";
        } else if (transactionDto.getAmount() <= 0) {
            redirectAttributes.addFlashAttribute("error", insufficientAmount);
            return "redirect:/main";
        }
        
        accountService.deposit(transactionDto.getAccount(), transactionDto.getAmount());

        transactionDto.setTransactionType(TransactionType.DEPOSIT);
        transactionService.registerTransaction(email, transactionDto);

        return "redirect:/main";
    }

    @PostMapping("/withdrawForm")
    public String withdrawForm(@ModelAttribute TransactionDto transactionDto, RedirectAttributes redirectAttributes) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        if (transactionDto.getAccount() == null) {
            redirectAttributes.addFlashAttribute("error", notSelectedErrorMsg);
            return "redirect:/main";
        } else if (transactionDto.getAmount() <= 0) {
            redirectAttributes.addFlashAttribute("error", insufficientAmount);
            return "redirect:/main";
        }

        accountService.withdraw(transactionDto.getAccount(), transactionDto.getAmount());

        transactionDto.setTransactionType(TransactionType.WITHDRAW);
        transactionService.registerTransaction(email, transactionDto);

        return "redirect:/main";
    }


    /* I created another transaction DTO to handle 2 account requests */
    @PostMapping("/transactionForm")
    public String transferForm(@ModelAttribute TransferRequest transferRequest, RedirectAttributes redirectAttributes) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        if (transferRequest.getAccountSource() == null || transferRequest.getAccountTarget() == null) {
            redirectAttributes.addFlashAttribute("error", notSelectedErrorMsg);
            return "redirect:/main";
        } else if (transferRequest.getAccountSource().equals(transferRequest.getAccountTarget())) {
            redirectAttributes.addFlashAttribute("error", sameSelectedErrorMsg);
            return "redirect:/main";
        } else if (transferRequest.getAmount() <= 0) {
            redirectAttributes.addFlashAttribute("error", insufficientAmount);
            return "redirect:/main";
        }

        accountService.transfer(transferRequest.getAccountSource(), transferRequest.getAccountTarget(), transferRequest.getAmount());
        
        TransactionDto transactionDto = new TransactionDto(
            transferRequest.getTransactionId(), 
            transferRequest.getAmount(), 
            TransactionType.TRANSFER,  
            transferRequest.getAccountSource()
        );

        transactionService.registerTransaction(email, transactionDto);
        return "redirect:/main";
    }
}
