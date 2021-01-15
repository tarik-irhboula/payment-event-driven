package com.sfeir.kata.notification.domain;

public class InvalidRecipientInput extends RuntimeException {
    InvalidRecipientInput(String msg) {
        super(msg);
    }
}
