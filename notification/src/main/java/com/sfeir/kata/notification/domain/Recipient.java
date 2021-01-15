package com.sfeir.kata.notification.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Recipient extends AbstractAggregateRoot<Recipient> {

    @Id
    private UUID id;
    private String email;
}
