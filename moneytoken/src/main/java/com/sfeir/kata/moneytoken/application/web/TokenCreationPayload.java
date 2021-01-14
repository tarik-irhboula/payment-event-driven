package com.sfeir.kata.moneytoken.application.web;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TokenCreationPayload {
    private BigDecimal amount;
}
