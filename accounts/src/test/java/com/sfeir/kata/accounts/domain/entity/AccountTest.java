package com.sfeir.kata.accounts.domain.entity;

import com.sfeir.kata.accounts.domain.Account;
import com.sfeir.kata.accounts.domain.InvalidAccountInput;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {

    @Test
    void shouldError_WhenCreatingNewAccountWithoutEmail() {
        // Given
        String email = null;
        Account.Type type = Account.Type.CLIENT;
        // When
        assertThrows(InvalidAccountInput.class, () -> {
            Account account = Account.create(type, email);
        }, "Invalid email");
    }

    @Test
    void shouldError_WhenCreatingNewAccountWithoutType() {
        // Given
        String email = "account@test.com";
        Account.Type type = null;
        // When
        assertThrows(InvalidAccountInput.class, () -> {
            Account account = Account.create(type, email);
        }, "Invalid account type");
    }

    @Test
    void returnEmptyAccountWithValidIdentifier_WhenCreatingNewAccount() {
        // Given
        String email = "account@test.com";
        Account.Type type = Account.Type.CLIENT;
        // When
        Account account = Account.create(type, email);
        // Then
        assertEquals(account.getBalance(), 0.0);
        assertEquals(account.getEmail(), email);
        assertEquals(account.getType(), type);
        assertNotNull(account.getId());
    }

}
