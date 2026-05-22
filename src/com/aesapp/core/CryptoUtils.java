package com.aesapp.core;

import java.util.Arrays;
import java.util.Base64;

public class CryptoUtils {
    private CryptoUtils() {} // Prevent instantiation

    public static String encodeBase64(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public static byte[] decodeBase64(String encoded) {
        return Base64.getDecoder().decode(encoded);
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static byte[] concatenate(byte[] a, byte[] b) {
        byte[] result = new byte[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    public static byte[][] splitIV(byte[] data, int ivLength) {
        byte[] iv = Arrays.copyOfRange(data, 0, ivLength);
        byte[] cipher = Arrays.copyOfRange(data, ivLength, data.length);
        return new byte[][]{iv, cipher};
    }
}