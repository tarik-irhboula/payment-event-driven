package com.sfeir.kata.moneytoken.domain.exception;

public class InvalidTokenInput extends RuntimeException {
    public InvalidTokenInput(String msg) {
        super(msg);
    }
}
