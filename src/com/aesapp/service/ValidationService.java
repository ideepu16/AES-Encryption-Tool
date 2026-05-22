package com.aesapp.service;

import java.util.Base64;

public class ValidationService {
    private static final int MIN_PASSPHRASE_LENGTH = 8;

    public void validateInput(String text, String passphrase) {
        validatePlainText(text); // Reused for ciphertext validation in this context
        validatePassphrase(passphrase);
    }

    public boolean validatePlainText(String text) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Text cannot be empty.");
        }
        return true;
    }

    public boolean validateCipherText(String text) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Cipher text cannot be empty.");
        }
        try {
            Base64.getDecoder().decode(text);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid Base64 format.");
        }
        return true;
    }

    public boolean validatePassphrase(String passphrase) {
        if (passphrase == null || passphrase.isEmpty()) {
            throw new IllegalArgumentException("Passphrase cannot be empty.");
        }
        if (passphrase.length() < MIN_PASSPHRASE_LENGTH) {
            throw new IllegalArgumentException("Passphrase must be at least 8 characters.");
        }
        return true;
    }
}