package com.sfeir.kata.accounts.application.message;

import lombok.Data;

import java.util.UUID;

@Data
public class AccountCreatedMessage implements AccountMessage {
    private UUID accountId;
    private String accountType;
    private String accountEmail;

    @Override
    public String fullyQualifiedName() {
        return "account.created";
    }
}
