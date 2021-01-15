package com.sfeir.kata.accounts.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
class Transaction {
    enum Type {
        CREDIT, DEBIT
    }

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    private Type type;

    private BigDecimal amount;
}
