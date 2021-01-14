package com.sfeir.kata.moneytoken.domain;

public class InvalidTokenInput extends RuntimeException {
    InvalidTokenInput(String msg) {
        super(msg);
    }
}
