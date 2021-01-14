package com.sfeir.kata.moneytoken.domain;

import lombok.Getter;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


@Getter
@Entity
public class Token extends AbstractAggregateRoot<Token> {
    private final static BigDecimal[] possibleAmounts = {
            new BigDecimal(100),
            new BigDecimal(200),
            new BigDecimal(1000)
    };

    @Id
    private String value;
    private BigDecimal amount;
    private boolean consumed;

    Token() {
    }

    Token(String value, BigDecimal amount, boolean consumed) {
        this.value = value;
        this.amount = amount;
        this.consumed = consumed;
    }

    static Token generate(BigDecimal amount) {
        if (amount == null || !List.of(possibleAmounts).contains(amount)) {
            throw new InvalidTokenInput("Invalid token amount");
        }
        return new Token(UUID.randomUUID().toString(), amount, false);
    }

    Token consume(UUID consumerId) {
        if (consumerId == null) {
            throw new InvalidTokenInput("Invalid token consumer");
        }
        if (this.consumed) {
            throw new InvalidTokenState("Token already consumed");
        }

        this.consumed = true;
        this.registerEvent(new TokenConsumed(consumerId, this.value));

        return this;
    }
}
