package com.aesapp.core;

import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESKeyGenerator {
    private static final String HASH_ALGORITHM = "SHA-256";
    private static final int KEY_SIZE_BYTES = 16; // 128 bits
    private static final String KEY_ALGORITHM = "AES";

    public SecretKey deriveKey(String passphrase) throws Exception {
        MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
        byte[] hash = digest.digest(passphrase.getBytes("UTF-8"));
        byte[] keyBytes = Arrays.copyOf(hash, KEY_SIZE_BYTES);
        return new SecretKeySpec(keyBytes, KEY_ALGORITHM);
    }
}