package com.sfeir.kata.accounts.application.processor;

import com.sfeir.kata.accounts.application.message.AccountChannel;
import com.sfeir.kata.sharedmodels.application.AccountCreatedMessage;
import com.sfeir.kata.accounts.domain.Account;
import com.sfeir.kata.accounts.domain.AccountCreated;
import com.sfeir.kata.accounts.domain.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class AccountingProcessor {

    private AccountRepository repo;
    private AccountChannel channel;

    @Autowired
    AccountingProcessor(AccountRepository repo, AccountChannel channel) {
        this.repo = repo;
        this.channel = channel;
    }

    @Async
    @TransactionalEventListener
    public void handleAccountCreationEvent(AccountCreated event) {
        repo.findById(event.getAccountId())
                .map(AccountingProcessor::createdMessage)
                .ifPresent(msg -> channel.sendMessage(msg));

    }

    private static AccountCreatedMessage createdMessage(Account account) {
        AccountCreatedMessage msg = new AccountCreatedMessage();
        msg.setAccountId(account.getId());
        msg.setAccountEmail(account.getEmail());
        msg.setAccountType(account.getType().toString());

        return msg;
    }
}
