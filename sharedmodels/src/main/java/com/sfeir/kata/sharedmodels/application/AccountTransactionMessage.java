package com.sfeir.kata.sharedmodels.application;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountTransactionMessage implements ApplicationMessage {
    public static final String COMMAND_TOPIC = "account_transaction_command";
    public static final String EVENT_TOPIC = "account_transaction";

    public enum Commands {
        SUPPLY_ACCOUNT, TRANSFERT_AMOUNT
    }

    public enum Events {
        ACCOUNT_SUPPLIED, ACCOUNT_SUPPLY_FAILED, AMOUNT_TRANSFERED, ACCOUNT_CREDITED, ACCOUNT_DEBITED
    }

    private UUID requestId;
    private UUID from;
    private UUID to;
    private BigDecimal amount;
    private String message;
}
