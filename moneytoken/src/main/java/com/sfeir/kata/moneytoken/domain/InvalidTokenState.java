package com.sfeir.kata.moneytoken.domain;

public class InvalidTokenState extends RuntimeException{
    InvalidTokenState(String msg) {
        super(msg);
    }
}
