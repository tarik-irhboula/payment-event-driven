package com.sfeir.kata.accounts.domain.exception;

public class InvalidAccountInput extends RuntimeException {

    public InvalidAccountInput(String message) {
        super(message);
    }
}
