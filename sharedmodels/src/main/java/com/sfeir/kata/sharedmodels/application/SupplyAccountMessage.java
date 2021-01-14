package com.sfeir.kata.sharedmodels.application;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class SupplyAccountMessage implements AccountTransactionMessage {
    private UUID accountId;
    private BigDecimal amount;

    @Override
    public String fullyQualifiedName() {
        return "supply.account";
    }
}
