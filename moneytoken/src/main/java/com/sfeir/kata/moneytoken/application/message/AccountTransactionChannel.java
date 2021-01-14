package com.sfeir.kata.moneytoken.application.message;

import com.sfeir.kata.sharedmodels.application.AccountTransactionMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class AccountTransactionChannel {
    private static final String ACCOUNT_TRANSACTION_TOPIC = "account_transaction";

    private KafkaTemplate<String, AccountTransactionMessage> template;

    @Autowired
    AccountTransactionChannel(KafkaTemplate<String, AccountTransactionMessage> template) {
        this.template = template;
    }

    public void sendMessage(AccountTransactionMessage msg) {
        template.send(ACCOUNT_TRANSACTION_TOPIC, msg.fullyQualifiedName(), msg);
    }
}
