package com.sfeir.kata.moneytoken.domain.event;

import com.sfeir.kata.sharedmodels.domain.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@AllArgsConstructor
@ToString
public class TokenCreated implements DomainEvent {
    private UUID tokenId;
}
