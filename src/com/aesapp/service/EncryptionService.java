package com.aesapp.service;

import com.aesapp.core.AESEngine;
import com.aesapp.core.CryptoUtils;
import javax.crypto.SecretKey;

public class EncryptionService {
    private final AESEngine aesEngine;
    private final KeyService keyService;
    private final ValidationService validationService;

    public EncryptionService() {
        this.aesEngine = new AESEngine();
        this.keyService = new KeyService();
        this.validationService = new ValidationService();
    }

    public String encrypt(String plainText, String passphrase) throws Exception {
        validationService.validateInput(plainText, passphrase);
        SecretKey key = keyService.generateKey(passphrase);
        byte[] encryptedBytes = aesEngine.encrypt(plainText, key);
        return CryptoUtils.encodeBase64(encryptedBytes);
    }

    public String decrypt(String cipherText, String passphrase) throws Exception {
        validationService.validateCipherText(cipherText);
        validationService.validatePassphrase(passphrase);
        
        SecretKey key = keyService.generateKey(passphrase);
        byte[] cipherBytes = CryptoUtils.decodeBase64(cipherText);
        return aesEngine.decrypt(cipherBytes, key);
    }
}