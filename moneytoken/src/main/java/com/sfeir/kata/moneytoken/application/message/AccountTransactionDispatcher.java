package com.sfeir.kata.moneytoken.application.message;

import com.sfeir.kata.moneytoken.domain.TokenService;
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

    TokenService tokenService;

    @Autowired
    AccountTransactionDispatcher(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @KafkaListener(topics = AccountTransactionMessage.EVENT_TOPIC)
    public void handleAccountTransactionEvent(@Payload AccountTransactionMessage msg,
                                              @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String event) {
        if (event.equals(AccountTransactionMessage.Events.ACCOUNT_SUPPLIED.toString())) {
            validateTokenConsumption(msg);
        }

        if (event.equals(AccountTransactionMessage.Events.ACCOUNT_SUPPLY_FAILED.toString())) {
            cancelTokenConsumption(msg);
        }
    }

    public void validateTokenConsumption(AccountTransactionMessage msg) {
        try {
            this.tokenService.validateTokenConsumption(msg.getTo(), msg.getRequestId());
        } catch (RuntimeException e) {
            log.error(e.getMessage());
        }
    }

    public void cancelTokenConsumption(AccountTransactionMessage msg) {
        try {
            this.tokenService.cancelTokenConsumption(msg.getRequestId());
        } catch (RuntimeException e) {
            log.error(e.getMessage());
        }
    }

}
