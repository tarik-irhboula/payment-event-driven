package com.sfeir.kata.notification.application.message;

import com.sfeir.kata.notification.domain.NotificationService;
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
public class AccountTransactionDispatcher {

    private NotificationService service;

    @Autowired
    AccountTransactionDispatcher(NotificationService service) {
        this.service = service;
    }

    @KafkaListener(topics = AccountTransactionMessage.EVENT_TOPIC)
    public void handleAccountTransactionCommand(@Payload AccountTransactionMessage msg,
                                                @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String event) {
        if (event.equals(AccountTransactionMessage.Events.ACCOUNT_CREDITED.toString())) {
            notifyAccountCredited(msg);
        }
    }

    public void notifyAccountCredited(AccountTransactionMessage msg) {
        try {
            this.service.notifyAccountCredited(msg.getTo(), msg.getAmount());
        } catch (RuntimeException e) {
            log.error(e.getMessage());
        }
    }
}
