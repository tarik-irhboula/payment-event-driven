package com.sfeir.kata.moneytoken.domain;


import com.sfeir.kata.sharedmodels.domain.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class TokenReadyForConsumption implements DomainEvent {
    private UUID consumerId;
    private String tokenId;
}
