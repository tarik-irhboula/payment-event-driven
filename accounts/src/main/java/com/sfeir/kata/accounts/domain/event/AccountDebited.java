package com.sfeir.kata.accounts.domain.event;

import com.sfeir.kata.sharedmodels.domain.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor
@ToString
public class AccountDebited implements DomainEvent {
    private UUID accountId;
    private BigDecimal amount;
}
