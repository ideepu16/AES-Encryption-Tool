package com.aesapp.core;

import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class AESEngine {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final int IV_SIZE = 16;

    public byte[] encrypt(String plainText, SecretKey key) throws Exception {
        IvParameterSpec iv = generateIV();
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes("UTF-8"));
        return CryptoUtils.concatenate(iv.getIV(), encryptedBytes);
    }

    public String decrypt(byte[] cipherData, SecretKey key) throws Exception {
        byte[][] parts = CryptoUtils.splitIV(cipherData, IV_SIZE);
        IvParameterSpec iv = new IvParameterSpec(parts[0]);
        
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        
        byte[] decryptedBytes = cipher.doFinal(parts[1]);
        return new String(decryptedBytes, "UTF-8");
    }

    private IvParameterSpec generateIV() {
        byte[] iv = new byte[IV_SIZE];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }
}