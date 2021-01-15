package com.sfeir.kata.moneytoken.domain.exception;

public class InvalidTokenState extends RuntimeException{
    public InvalidTokenState(String msg) {
        super(msg);
    }
}
