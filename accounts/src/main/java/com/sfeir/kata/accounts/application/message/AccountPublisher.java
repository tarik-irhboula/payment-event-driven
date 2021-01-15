package com.sfeir.kata.accounts.application.message;

import com.sfeir.kata.sharedmodels.application.AccountMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class AccountPublisher {
    private KafkaTemplate<String, AccountMessage> template;

    @Autowired
    AccountPublisher(KafkaTemplate<String, AccountMessage> template) {
        this.template = template;
    }

    public void sendCommand(AccountMessage.Commands cmd, AccountMessage msg) {
        template.send(AccountMessage.COMMAND_TOPIC, cmd.toString(), msg);
    }

    public void sendEvent(AccountMessage.Events event, AccountMessage msg) {
        template.send(AccountMessage.EVENT_TOPIC, event.toString(), msg);
    }

}
