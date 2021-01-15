package com.sfeir.kata.notification.domain;

import com.sfeir.kata.notification.domain.event.ActorNotified;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@Transactional(rollbackFor = {RuntimeException.class})
@Slf4j
public class NotificationService {

    private RecipientRepository recipientRepository;
    private ApplicationEventPublisher bus;

    @Autowired
    NotificationService(RecipientRepository recipientRepository, ApplicationEventPublisher bus) {
        this.recipientRepository = recipientRepository;
        this.bus = bus;
    }

    public UUID addRecipient(UUID recipientId, String email) {
        if (email == null) throw new InvalidRecipientInput("Invalid recipient email");
        if (this.recipientRepository.findById(recipientId).isPresent())
            throw new InvalidRecipientInput("Invalid recipient id. Recipient already exist");

        Recipient recipient = new Recipient();
        recipient.setId(recipientId);
        recipient.setEmail(email);

        this.recipientRepository.save(recipient);

        return recipient.getId();
    }

    public void notifyAccountCredited(UUID recipientId, BigDecimal amount) {
        Recipient recipient = this.recipientRepository.findById(recipientId)
                .orElseThrow(() -> new InvalidRecipientInput("Invalid recipient id, Recipient not found"));

        log.info("NOTIFYING BY EMAIL<" + recipient.getEmail() + "> : YOUR ACCOUNT HAS BEED CREDITED WITH AN AMOUNT OF : " + amount);

        bus.publishEvent(new ActorNotified(recipientId, "Account Credited"));
    }
}
