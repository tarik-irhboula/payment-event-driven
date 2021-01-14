package com.sfeir.kata.accounts.application.web;

import com.sfeir.kata.accounts.domain.AccountingService;
import com.sfeir.kata.accounts.domain.InvalidAccountInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private AccountingService accountingService;

    @Autowired
    AccountController(AccountingService accountingService) {
        this.accountingService = accountingService;
    }

    @PostMapping
    @ResponseBody
    public Object createAccount(@RequestParam String type, @RequestParam String email) {
        try {
            return this.accountingService.createAccount(type, email);
        } catch (InvalidAccountInput e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
