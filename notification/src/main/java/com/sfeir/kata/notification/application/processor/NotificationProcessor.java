package com.sfeir.kata.notification.application.processor;

import com.sfeir.kata.notification.application.message.NotificationPublisher;
import com.sfeir.kata.notification.domain.event.ActorNotified;
import com.sfeir.kata.sharedmodels.application.AccountMessage;
import com.sfeir.kata.sharedmodels.application.NotificationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class NotificationProcessor {

    NotificationPublisher publisher;

    @Autowired
    NotificationProcessor(NotificationPublisher publisher) {
        this.publisher = publisher;
    }

    @Async
    @TransactionalEventListener
    public void handleActorNotified(ActorNotified event) {
        NotificationMessage msg = new NotificationMessage(event.getActorId());
        this.publisher.sendEvent(NotificationMessage.Events.ACTOR_NOTIFIED, msg);
    }
}
