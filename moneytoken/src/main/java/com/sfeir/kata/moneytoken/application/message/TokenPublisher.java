package com.sfeir.kata.moneytoken.application.message;

import com.sfeir.kata.sharedmodels.application.TokenMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TokenPublisher {
    private KafkaTemplate<String, TokenMessage> template;

    @Autowired
    TokenPublisher(KafkaTemplate<String, TokenMessage> template) {
        this.template = template;
    }

    public void sendCommand(TokenMessage.Commands cmd, TokenMessage msg) {
        template.send(TokenMessage.COMMAND_TOPIC, cmd.toString(), msg);
    }

    public void sendEvent(TokenMessage.Events event, TokenMessage msg) {
        template.send(TokenMessage.EVENT_TOPIC, event.toString(), msg);
    }
}
