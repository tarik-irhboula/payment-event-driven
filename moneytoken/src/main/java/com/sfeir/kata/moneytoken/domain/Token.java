package com.sfeir.kata.moneytoken.domain;

import com.sfeir.kata.moneytoken.domain.event.TokenConsumed;
import com.sfeir.kata.moneytoken.domain.event.TokenConsumptionCanceled;
import com.sfeir.kata.moneytoken.domain.event.TokenCreated;
import com.sfeir.kata.moneytoken.domain.event.TokenReadyForConsumption;
import com.sfeir.kata.moneytoken.domain.exception.InvalidTokenInput;
import com.sfeir.kata.moneytoken.domain.exception.InvalidTokenState;
import lombok.Getter;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


@Getter
@Entity
public class Token extends AbstractAggregateRoot<Token> {
    enum State {
        CREATED, CONSUMPTION_PENDING, CONSUMED
    }

    private final static BigDecimal[] possibleAmounts = {
            new BigDecimal(100),
            new BigDecimal(200),
            new BigDecimal(1000)
    };

    @Id
    private UUID id;

    private String value;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private State state;

    //TODO Remove public
    public Token() {
    }

    Token(String value, BigDecimal amount, State state) {
        this.id = UUID.randomUUID();
        this.value = value;
        this.amount = amount;
        this.state = state;

        this.registerEvent(new TokenCreated(this.id));
    }

    static Token generate(BigDecimal amount) {
        if (amount == null || !List.of(possibleAmounts).contains(amount)) {
            throw new InvalidTokenInput("Invalid token amount");
        }
        return new Token(UUID.randomUUID().toString(), amount, State.CREATED);
    }

    Token prepareConsumption(UUID consumerId) {
        if (consumerId == null) throw new InvalidTokenInput("Invalid token consumer");
        if (!State.CREATED.equals(this.state)) throw new InvalidTokenState("Token invalid state");

        this.state = State.CONSUMPTION_PENDING;
        this.registerEvent(new TokenReadyForConsumption(consumerId, this.id));

        return this;
    }

    Token cancelConsumption() {
        if (!State.CONSUMPTION_PENDING.equals(this.state)) throw new InvalidTokenState("Token invalid state");

        this.state = State.CREATED;
        this.registerEvent(new TokenConsumptionCanceled(this.id));

        return this;
    }

    Token consume(UUID consumerId) {
        if (consumerId == null) throw new InvalidTokenInput("Invalid token consumer");
        if (!State.CONSUMPTION_PENDING.equals(this.state)) throw new InvalidTokenState("Token invalid state");

        this.state = State.CONSUMED;
        this.registerEvent(new TokenConsumed(consumerId, this.id));

        return this;
    }
}
