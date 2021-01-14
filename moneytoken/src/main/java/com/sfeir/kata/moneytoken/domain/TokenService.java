package com.sfeir.kata.moneytoken.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@Transactional(rollbackFor = {RuntimeException.class})
public class TokenService {

    TokenRepository repo;

    @Autowired
    TokenService(TokenRepository repo) {
        this.repo = repo;
    }

    public String generateToken(BigDecimal amount) {
        Token token = Token.generate(amount);

        this.repo.save(token);

        return token.getValue();
    }

    public void prepareTokenConsumption(UUID consumerId, String tokenString) {
        Token token = this.repo.findById(tokenString)
                .orElseThrow(() -> new InvalidTokenInput("Invalid token string. Token not found"));

        token.prepareConsumption(consumerId);

        this.repo.save(token);
    }

    public void validateTokenConsumption(UUID consumerId, String tokenString) {
        Token token = this.repo.findById(tokenString)
                .orElseThrow(() -> new InvalidTokenInput("Invalid token string. Token not found"));

        token.consume(consumerId);

        this.repo.save(token);
    }

}
