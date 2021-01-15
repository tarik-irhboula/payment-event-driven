package com.sfeir.kata.accounts.domain.event;

import com.sfeir.kata.sharedmodels.domain.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

import java.math.BigDecimal;
import java.util.UUID;


@Getter
@AllArgsConstructor
@ToString
public class AccountSupplyFailed implements DomainEvent {
    private UUID requestId;
    private UUID accountId;
    private BigDecimal amount;
    private String reason;
}

