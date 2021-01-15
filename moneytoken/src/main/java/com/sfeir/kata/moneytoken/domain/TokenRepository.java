package com.sfeir.kata.moneytoken.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository extends CrudRepository<Token, UUID> {

    public Optional<Token> findFirstByValue(String value);
}
