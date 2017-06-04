package com.mvc.model;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class Hasher {

    private static final Integer itteration = 20000;
    private static final Integer saltLen = 256;
    private static final Integer keyLen = 256;

    public  String hashSaltPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {

        Base64.Encoder encoder = Base64.getEncoder();
        byte[] salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLen);
        return  encoder.encodeToString(salt) + "&" + hash(password, salt);
    }

    public boolean compareValues(String password, String stored, String salt) throws InvalidKeySpecException, NoSuchAlgorithmException {

        byte[] decodedSalt = Base64.getDecoder().decode(salt);
        return hash(password, decodedSalt).equals(stored);
    }

    private String hash(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        SecretKey key = factory.generateSecret(new PBEKeySpec(password.toCharArray(), salt, itteration, keyLen));
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(key.getEncoded());
    }
}
