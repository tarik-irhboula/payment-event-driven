package com.sfeir.kata.accounts;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@SpringBootApplication
public class AccountsApplication implements CommandLineRunner {

    public static Logger logger = LoggerFactory.getLogger(AccountsApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AccountsApplication.class, args);
    }

    @Autowired
    private KafkaTemplate<String,Account> template;

    private final CountDownLatch latch = new CountDownLatch(3);
    @Override
    public void run(String... args) throws Exception {
        this.template.send("Accounts",new Account(0L,300.0));
        latch.await(60, TimeUnit.SECONDS);
        logger.info("All received");
    }

    @KafkaListener(topics = "Accounts", groupId = "account")
    public void listen(Account cr){
        logger.info(cr.toString());
        latch.countDown();
    }


}
