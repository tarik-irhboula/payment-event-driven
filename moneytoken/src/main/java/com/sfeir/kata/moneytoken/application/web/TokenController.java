package com.sfeir.kata.moneytoken.application.web;

import com.sfeir.kata.moneytoken.domain.InvalidTokenInput;
import com.sfeir.kata.moneytoken.domain.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tokens")
public class TokenController {

    TokenService tokenService;

    @Autowired
    TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping
    public Object createToken(@RequestBody TokenCreationPayload payload) {
        try {
            return this.tokenService.generateToken(payload.getAmount());
        } catch (InvalidTokenInput e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @PutMapping("/consume")
    public Object consumeToken(@RequestBody TokenConsumptionPayload payload) {
        try {
            this.tokenService.prepareTokenConsumption(payload.getConsumerId(), payload.getTokenString());
            this.tokenService.validateTokenConsumption(payload.getConsumerId(), payload.getTokenString());
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (InvalidTokenInput e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
