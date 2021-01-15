package com.sfeir.kata.notification.domain.event;

import com.sfeir.kata.sharedmodels.domain.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;


@Getter
@AllArgsConstructor
@ToString
public class ActorNotified implements DomainEvent {
    private UUID actorId;
    private String message;
}
