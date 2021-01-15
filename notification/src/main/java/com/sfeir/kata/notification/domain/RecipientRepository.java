package com.sfeir.kata.notification.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RecipientRepository extends CrudRepository<Recipient, UUID> {
}
