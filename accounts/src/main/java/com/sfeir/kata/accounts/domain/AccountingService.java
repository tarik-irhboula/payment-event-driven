package com.sfeir.kata.accounts.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AccountingService {

    private AccountRepository repo;

    @Autowired
    AccountingService(AccountRepository repo) {
        this.repo = repo;
    }

    @Transactional(rollbackFor = {RuntimeException.class})
    public UUID createAccount(String type, String email) {
        Account.Type resolvedType = null;
        try {
            resolvedType = Account.Type.valueOf(type);
        } catch (IllegalArgumentException e) {
            throw new InvalidAccountInput("Invalid account type");
        }
        Account account = Account.create(resolvedType, email);

        this.repo.save(account);

        return account.getId();
    }
}
