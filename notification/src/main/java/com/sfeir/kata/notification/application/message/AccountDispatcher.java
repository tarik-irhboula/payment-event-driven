package com.sfeir.kata.notification.application.message;

import com.sfeir.kata.notification.domain.NotificationService;
import com.sfeir.kata.sharedmodels.application.AccountMessage;
import com.sfeir.kata.sharedmodels.application.AccountTransactionMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AccountDispatcher {

    private NotificationService service;

    @Autowired
    AccountDispatcher(NotificationService service) {
        this.service = service;
    }

    @KafkaListener(topics = AccountMessage.EVENT_TOPIC)
    public void handleAccountTransactionCommand(@Payload AccountMessage msg,
                                                @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String event) {
        if (event.equals(AccountMessage.Events.ACCOUNT_CREATED.toString())) {
            addRecipient(msg);
        }
    }

    public void addRecipient(AccountMessage msg) {
        try {
            this.service.addRecipient(msg.getAccountId(), msg.getAccountEmail());
        } catch (RuntimeException e) {
            log.error(e.getMessage());
        }
    }
}
