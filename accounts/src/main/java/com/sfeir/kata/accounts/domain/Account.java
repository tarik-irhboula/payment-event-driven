package com.sfeir.kata.accounts.domain;

import lombok.Getter;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.util.UUID;

@Getter
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

    private Double balance;

    Account() {
    }

    Account(UUID id, Type type, String email, Double balance) {
        super();

        this.id = id;
        this.type = type;
        this.email = email;
        this.balance = balance;

        this.registerEvent(new AccountCreated(this.id));
    }

    public static Account create(Type type, String email) {
        if (type == null) throw new InvalidAccountInput("Invalid account type");
        if (email == null) throw new InvalidAccountInput("Invalid email");

        return new Account(UUID.randomUUID(), type, email, 0.0);
    }
}
