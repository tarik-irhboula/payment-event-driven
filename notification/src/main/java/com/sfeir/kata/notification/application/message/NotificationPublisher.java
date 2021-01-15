package com.sfeir.kata.notification.application.message;

import com.sfeir.kata.sharedmodels.application.NotificationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationPublisher {
    private KafkaTemplate<String, NotificationMessage> template;

    @Autowired
    NotificationPublisher(KafkaTemplate<String, NotificationMessage> template) {
        this.template = template;
    }

    public void sendCommand(NotificationMessage.Commands cmd, NotificationMessage msg) {

        template.send(NotificationMessage.COMMAND_TOPIC, cmd.toString(), msg);
    }

    public void sendEvent(NotificationMessage.Events event, NotificationMessage msg) {
        template.send(NotificationMessage.EVENT_TOPIC, event.toString(), msg);
    }
}
