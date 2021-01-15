package com.sfeir.kata.sharedmodels.application;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountMessage implements ApplicationMessage {
    public static final String COMMAND_TOPIC = "account_command";
    public static final String EVENT_TOPIC = "account";

    public enum Commands {}

    public enum Events {
        ACCOUNT_CREATED
    }

    private UUID accountId;
    private String accountType;
    private String accountEmail;
}
