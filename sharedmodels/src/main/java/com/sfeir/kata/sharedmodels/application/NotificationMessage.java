package com.sfeir.kata.sharedmodels.application;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationMessage {
    public static final String COMMAND_TOPIC = "notification_command";
    public static final String EVENT_TOPIC = "notification";

    public enum Commands {}

    public enum Events {
        ACTOR_NOTIFIED
    }

    private UUID actorId;
}
