package com.dairyFarm.dairyFarm.Impl;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptionConfigImpl {
    
    // Method to derive a 16-byte key from the given key string
    private static SecretKey getKeyFromString(String key) throws NoSuchAlgorithmException {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        keyBytes = sha.digest(keyBytes);
        return new SecretKeySpec(keyBytes, 0, 16, "AES");
    }

    // Encrypt a password using the given key
    public static String encrypt(String password, String key) throws Exception {
        SecretKey secretKey = getKeyFromString(key);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(password.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Decrypt an encrypted password using the given key
    public static String decrypt(String encryptedPassword, String key) throws Exception {
        SecretKey secretKey = getKeyFromString(key);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

//    public static void main(String[] args) {
//        try {
//            String key = "8448711"; // Your key
//            String password = "MySecurePassword";
//
//            // Encrypting the password
//            String encryptedPassword = encrypt(password, key);
//            System.out.println("Encrypted: " + encryptedPassword);
//
//            // Decrypting the password
//            String decryptedPassword = decrypt(encryptedPassword, key);
//            System.out.println("Decrypted: " + decryptedPassword);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
