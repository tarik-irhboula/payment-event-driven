package com.sfeir.kata.accounts.domain;

import com.sfeir.kata.accounts.domain.event.AccountCreated;
import com.sfeir.kata.accounts.domain.event.AccountCredited;
import com.sfeir.kata.accounts.domain.event.AccountDebited;
import com.sfeir.kata.accounts.domain.exception.InvalidAccountInput;
import com.sfeir.kata.accounts.domain.exception.InvalidAccountState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Account extends AbstractAggregateRoot<Account> {
    public enum Type {
        CLIENT, PROVIDER
    }

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    private Type type;

    private String email;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "account_id")
    private List<Transaction> transactions;

    private Account(UUID id, Type type, String email, List<Transaction> transactions) {
        super();

        this.id = id;
        this.type = type;
        this.email = email;
        this.transactions = transactions;
        this.registerEvent(new AccountCreated(this.id));
    }

    static Account create(Type type, String email) {
        if (type == null) throw new InvalidAccountInput("Invalid account type");
        if (email == null) throw new InvalidAccountInput("Invalid email");

        return new Account(UUID.randomUUID(), type, email, new ArrayList<>());
    }

    Account credit(BigDecimal amount) {
        if (amount == null || amount.compareTo(new BigDecimal(0)) == -1)
            throw new InvalidAccountInput("Invalid credited amount");

        this.transactions.add(new Transaction(UUID.randomUUID(), Transaction.Type.CREDIT, amount));
        this.registerEvent(new AccountCredited(this.id, amount));

        return this;
    }

    Account debit(BigDecimal amount) {
        if (amount == null || amount.compareTo(new BigDecimal(0)) == -1)
            throw new InvalidAccountInput("Invalid debited amount");
        if (this.getBalance().compareTo(amount) == -1)
            throw new InvalidAccountState("Invalid account state. Not enough balance");

        this.transactions.add(new Transaction(UUID.randomUUID(), Transaction.Type.DEBIT, amount));
        this.registerEvent(new AccountDebited(this.id, amount));

        return this;
    }

    BigDecimal getBalance() {
        return this.transactions.stream()
                .map(t -> t.getType().equals(Transaction.Type.CREDIT) ? t.getAmount() : t.getAmount().negate())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
