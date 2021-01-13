package com.sfeir.kata.accounts.domain;

import com.sfeir.kata.shared.domain.DomainEvent;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
public class AccountCreated implements DomainEvent {
    private UUID accountId;

    AccountCreated(UUID accountId) {
        this.accountId = accountId;
    }
}
