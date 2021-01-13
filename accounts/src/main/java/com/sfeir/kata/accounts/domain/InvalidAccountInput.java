package com.sfeir.kata.accounts.domain;

public class InvalidAccountInput extends RuntimeException {

    public InvalidAccountInput(String message) {
        super(message);
    }
}
