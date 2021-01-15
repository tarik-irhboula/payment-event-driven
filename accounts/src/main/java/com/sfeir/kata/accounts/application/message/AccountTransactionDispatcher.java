package com.sfeir.kata.accounts.application.message;

import com.sfeir.kata.accounts.domain.AccountingService;
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

    private AccountingService accountingService;

    @Autowired
    AccountTransactionDispatcher(AccountingService accountingService) {
        this.accountingService = accountingService;
    }

    @KafkaListener(topics = AccountTransactionMessage.COMMAND_TOPIC)
    public void handleAccountTransactionCommand(@Payload AccountTransactionMessage msg,
                                                @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String event) {
        if (event.equals(AccountTransactionMessage.Commands.SUPPLY_ACCOUNT.toString())) {
            supplyAccount(msg);
        }
    }

    public void supplyAccount(AccountTransactionMessage msg) {
        try {
            this.accountingService.supplyAccount(msg.getTo(), msg.getAmount(), msg.getRequestId());
        } catch (RuntimeException e) {
            log.error(e.getMessage());
        }
    }

}
