package com.sfeir.kata.accounts.domain;

import com.sfeir.kata.accounts.domain.event.AccountCredited;
import com.sfeir.kata.accounts.domain.event.AccountSupplied;
import com.sfeir.kata.accounts.domain.event.AccountSupplyFailed;
import com.sfeir.kata.accounts.domain.exception.InvalidAccountInput;
import com.sfeir.kata.accounts.domain.exception.InvalidAccountState;
import com.sfeir.kata.sharedmodels.application.AccountTransactionMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@Transactional(rollbackFor = {RuntimeException.class})
public class AccountingService {

    private AccountRepository repo;
    private ApplicationEventPublisher bus;

    @Autowired
    AccountingService(AccountRepository repo, ApplicationEventPublisher bus) {
        this.repo = repo;
        this.bus = bus;
    }

    public UUID createAccount(String type, String email) {
        Account.Type resolvedType = null;
        try {
            resolvedType = Account.Type.valueOf(type);
        } catch (IllegalArgumentException e) {
            throw new InvalidAccountInput("Invalid account type");
        }
        Account account = Account.create(resolvedType, email);

        this.repo.save(account);

        return account.getId();
    }

    public UUID supplyAccount(UUID accountId, BigDecimal amount, UUID requestId) {
        try {
            Account account = this.repo.findById(accountId)
                    .orElseThrow(() -> new InvalidAccountInput("Invalid account id. Account not found"));

            if (!Account.Type.CLIENT.equals(account.getType()))
                throw new InvalidAccountInput("Invalid account id, Account is not a client");

            account.credit(amount);
            this.bus.publishEvent(new AccountSupplied(requestId, accountId, amount));

            this.repo.save(account);

            return account.getId();
        } catch (InvalidAccountInput | InvalidAccountState e) {
            this.bus.publishEvent(new AccountSupplyFailed(requestId, accountId, amount, e.getMessage()));
            throw e;
        }
    }

}
