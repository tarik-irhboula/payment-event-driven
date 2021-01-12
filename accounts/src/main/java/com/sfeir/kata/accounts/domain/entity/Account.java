package com.sfeir.kata.accounts.domain.entity;

import com.sfeir.kata.accounts.domain.exception.InvalidAccountInput;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
public class Account {
    public enum AccountType {
        CLIENT, PROVIDER;

        public static AccountType resolve(String type) throws Exception {
            switch (type) {
                case "provider":
                    return PROVIDER;
                case "client":
                    return CLIENT;
                default:
                    throw new Exception("Invalid account type");
            }
        }
    }

    private UUID id;
    private String email;
    private Double balance;
    private AccountType type;

    private Account() { }

    public static Account create(Account.AccountType type, String email) {
        if (type == null) throw new InvalidAccountInput("Invalid account type");
        if (email == null) throw new InvalidAccountInput("Invalid email");

        var account = new Account();
        account.id = UUID.randomUUID();
        account.type = type;
        account.email = email;
        account.balance = 0.0;

        return account;
    }
}
