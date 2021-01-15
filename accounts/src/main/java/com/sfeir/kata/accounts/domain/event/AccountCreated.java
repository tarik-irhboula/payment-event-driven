package com.sfeir.kata.accounts.domain.event;

import com.sfeir.kata.sharedmodels.domain.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@AllArgsConstructor
@ToString
public class AccountCreated implements DomainEvent {
    private UUID accountId;
}
