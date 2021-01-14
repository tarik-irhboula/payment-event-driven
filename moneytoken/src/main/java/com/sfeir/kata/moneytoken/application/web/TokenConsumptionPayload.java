package com.sfeir.kata.moneytoken.application.web;

import lombok.Data;

import java.util.UUID;

@Data
public class TokenConsumptionPayload {
   private UUID consumerId;
   private String tokenString;
}
