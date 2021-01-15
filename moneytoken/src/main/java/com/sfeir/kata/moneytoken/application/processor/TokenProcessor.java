package com.sfeir.kata.moneytoken.application.processor;

import com.sfeir.kata.moneytoken.application.message.AccountTransactionPublisher;
import com.sfeir.kata.moneytoken.application.message.TokenPublisher;
import com.sfeir.kata.moneytoken.domain.TokenRepository;
import com.sfeir.kata.moneytoken.domain.event.TokenConsumed;
import com.sfeir.kata.moneytoken.domain.event.TokenConsumptionCanceled;
import com.sfeir.kata.moneytoken.domain.event.TokenReadyForConsumption;
import com.sfeir.kata.sharedmodels.application.AccountTransactionMessage;
import com.sfeir.kata.sharedmodels.application.TokenMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class TokenProcessor {

    private TokenRepository repo;
    private AccountTransactionPublisher accountTransactionPublisher;
    private TokenPublisher tokenPublisher;

    @Autowired
    TokenProcessor(TokenRepository repo,
                   AccountTransactionPublisher accountTransactionPublisher,
                   TokenPublisher tokenPublisher) {
        this.repo = repo;
        this.accountTransactionPublisher = accountTransactionPublisher;
        this.tokenPublisher = tokenPublisher;
    }

    @Async
    @TransactionalEventListener
    public void handleTokenReadyForConsumptionEvent(TokenReadyForConsumption event) {
        this.repo.findById(event.getTokenId())
                .map(token -> new AccountTransactionMessage(
                        token.getId(), null, event.getConsumerId(), token.getAmount(), null))
                .ifPresent(msg -> accountTransactionPublisher.sendCommand(AccountTransactionMessage.Commands.SUPPLY_ACCOUNT, msg));

        TokenMessage msg = new TokenMessage(event.getTokenId());
        tokenPublisher.sendEvent(TokenMessage.Events.TOKEN_READY_FOR_CONSUMPTION, msg);
    }

    @Async
    @TransactionalEventListener
    public void handleTokenConsumedEvent(TokenConsumed event) {
        TokenMessage msg = new TokenMessage(event.getTokenId());
        tokenPublisher.sendEvent(TokenMessage.Events.TOKEN_CONSUMED, msg);
    }

    @Async
    @TransactionalEventListener
    public void handleTokenConsumptionCanceledEvent(TokenConsumptionCanceled event) {
        TokenMessage msg = new TokenMessage(event.getTokenId());
        tokenPublisher.sendEvent(TokenMessage.Events.TOKEN_CONSUMPTION_CANCELED, msg);
    }

}
