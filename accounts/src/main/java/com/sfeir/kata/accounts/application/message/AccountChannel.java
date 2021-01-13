package com.sfeir.kata.accounts.application.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class AccountChannel {
    private static final String ACCOUNT_TOPIC = "account_events";

    private KafkaTemplate<String, AccountMessage> template;

    @Autowired
    AccountChannel(KafkaTemplate<String, AccountMessage> template) {
        this.template = template;
    }

    public void sendMessage(AccountMessage msg) {
        template.send(ACCOUNT_TOPIC, msg.fullyQualifiedName(), msg);
    }
}
