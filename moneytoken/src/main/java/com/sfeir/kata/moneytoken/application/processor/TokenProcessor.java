package com.sfeir.kata.moneytoken.application.processor;

import com.sfeir.kata.moneytoken.application.message.AccountTransactionChannel;
import com.sfeir.kata.moneytoken.domain.Token;
import com.sfeir.kata.moneytoken.domain.TokenConsumed;
import com.sfeir.kata.moneytoken.domain.TokenRepository;
import com.sfeir.kata.sharedmodels.application.SupplyAccountMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Service
public class TokenProcessor {

    private TokenRepository repo;
    private AccountTransactionChannel channel;

    @Autowired
    TokenProcessor(TokenRepository repo, AccountTransactionChannel channel) {
        this.repo = repo;
        this.channel = channel;
    }

    @Async
    @TransactionalEventListener
    public void handleTokenConsumption(TokenConsumed event) {
        this.repo.findById(event.getTokenId())
                .map(token -> createMessage(event.getConsumerId(), token))
                .ifPresent(msg -> channel.sendMessage(msg));
    }


    private static SupplyAccountMessage createMessage(UUID consumerId, Token token) {
        SupplyAccountMessage msg = new SupplyAccountMessage();
        msg.setAccountId(consumerId);
        msg.setAmount(token.getAmount());

        return msg;
    }
}
