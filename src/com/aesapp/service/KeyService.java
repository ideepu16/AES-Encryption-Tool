package com.aesapp.service;

import com.aesapp.core.AESKeyGenerator;
import javax.crypto.SecretKey;

public class KeyService {
    private static final int KEY_SIZE = 128;
    private static final int MIN_PASSPHRASE_LENGTH = 8;
    private final AESKeyGenerator keyGenerator;

    public KeyService() {
        this.keyGenerator = new AESKeyGenerator();
    }

    public SecretKey generateKey(String passphrase) throws Exception {
        return keyGenerator.deriveKey(passphrase);
    }

    public boolean isValidPassphrase(String passphrase) {
        if (passphrase == null || passphrase.trim().isEmpty()) return false;
        return passphrase.length() >= MIN_PASSPHRASE_LENGTH;
    }

    public int getRequiredKeyLength() {
        return KEY_SIZE;
    }
}