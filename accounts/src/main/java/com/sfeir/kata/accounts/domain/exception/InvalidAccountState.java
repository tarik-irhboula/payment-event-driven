package com.sfeir.kata.accounts.domain.exception;

public class InvalidAccountState extends RuntimeException {

    public InvalidAccountState(String message) {
        super(message);
    }
}
