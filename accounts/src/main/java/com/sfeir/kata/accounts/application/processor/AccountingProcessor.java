package com.sfeir.kata.accounts.application.processor;

import com.sfeir.kata.accounts.application.message.AccountPublisher;
import com.sfeir.kata.accounts.application.message.AccountTransactionPublisher;
import com.sfeir.kata.accounts.domain.AccountRepository;
import com.sfeir.kata.accounts.domain.event.AccountCreated;
import com.sfeir.kata.accounts.domain.event.AccountCredited;
import com.sfeir.kata.accounts.domain.event.AccountSupplied;
import com.sfeir.kata.accounts.domain.event.AccountSupplyFailed;
import com.sfeir.kata.sharedmodels.application.AccountMessage;
import com.sfeir.kata.sharedmodels.application.AccountTransactionMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class AccountingProcessor {

    private AccountRepository repo;
    private AccountPublisher accountPublisher;
    private AccountTransactionPublisher accountTransactionPublisher;

    @Autowired
    AccountingProcessor(AccountRepository repo,
                        AccountPublisher accountPublisher,
                        AccountTransactionPublisher accountTransactionPublisher) {
        this.repo = repo;
        this.accountPublisher = accountPublisher;
        this.accountTransactionPublisher = accountTransactionPublisher;
    }

    @Async
    @TransactionalEventListener
    public void handleAccountCreationEvent(AccountCreated event) {
        repo.findById(event.getAccountId())
                .map((account -> new AccountMessage(account.getId(), account.getType().toString(), account.getEmail())))
                .ifPresent(msg -> this.accountPublisher.sendEvent(AccountMessage.Events.ACCOUNT_CREATED, msg));
    }

    @Async
    @TransactionalEventListener
    public void handleAccountCreditedEvent(AccountCredited event) {
        AccountTransactionMessage msg = new AccountTransactionMessage(
                null, null, event.getAccountId(), event.getAmount(), null);
        this.accountTransactionPublisher.sendEvent(AccountTransactionMessage.Events.ACCOUNT_CREDITED, msg);
    }

    @Async
    @TransactionalEventListener
    public void handleAccountSuppliedEvent(AccountSupplied event) {
        AccountTransactionMessage msg = new AccountTransactionMessage(
                event.getRequestId(), null, event.getAccountId(), event.getAmount(), null);
        this.accountTransactionPublisher.sendEvent(AccountTransactionMessage.Events.ACCOUNT_SUPPLIED, msg);
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void handleAccountSupplyFailedEvent(AccountSupplyFailed event) {
        AccountTransactionMessage msg = new AccountTransactionMessage(
                event.getRequestId(), null, event.getAccountId(), event.getAmount(), event.getReason());
        this.accountTransactionPublisher.sendEvent(AccountTransactionMessage.Events.ACCOUNT_SUPPLY_FAILED, msg);
    }
}
