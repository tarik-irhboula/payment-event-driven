package com.sfeir.kata.moneytoken.domain;

import com.sfeir.kata.moneytoken.domain.exception.InvalidTokenInput;
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

    public UUID prepareTokenConsumption(UUID consumerId, String tokenString) {
        Token token = this.repo.findFirstByValue(tokenString)
                .orElseThrow(() -> new InvalidTokenInput("Invalid token string. Token not found"));

        token.prepareConsumption(consumerId);

        this.repo.save(token);

        return token.getId();
    }

    public UUID validateTokenConsumption(UUID consumerId, UUID tokenId) {
        Token token = this.repo.findById(tokenId)
                .orElseThrow(() -> new InvalidTokenInput("Invalid token string. Token not found"));

        token.consume(consumerId);

        this.repo.save(token);

        return token.getId();
    }

    public UUID cancelTokenConsumption(UUID tokenId) {
        Token token = this.repo.findById(tokenId)
                .orElseThrow(() -> new InvalidTokenInput("Invalid token string. Token not found"));

        token.cancelConsumption();

        this.repo.save(token);

        return token.getId();

    }
}
