package com.sfeir.kata.sharedmodels.application;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenMessage {
    public static final String COMMAND_TOPIC = "token_command";
    public static final String EVENT_TOPIC = "token";

    public enum Commands {}

    public enum Events {
        TOKEN_CONSUMED, TOKEN_READY_FOR_CONSUMPTION, TOKEN_CONSUMPTION_CANCELED
    }

    private UUID tokenId;
}
