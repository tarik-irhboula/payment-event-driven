package com.sfeir.kata.moneytoken.application.message;

import com.sfeir.kata.sharedmodels.application.AccountTransactionMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class AccountTransactionPublisher {
    private KafkaTemplate<String, AccountTransactionMessage> template;

    @Autowired
    AccountTransactionPublisher(KafkaTemplate<String, AccountTransactionMessage> template) {
        this.template = template;
    }

    public void sendCommand(AccountTransactionMessage.Commands cmd, AccountTransactionMessage msg) {
        template.send(AccountTransactionMessage.COMMAND_TOPIC, cmd.toString(), msg);
    }

    public void sendEvent(AccountTransactionMessage.Events event, AccountTransactionMessage msg) {
        template.send(AccountTransactionMessage.EVENT_TOPIC, event.toString(), msg);
    }
}
