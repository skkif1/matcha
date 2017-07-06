package com.matcha.model;

import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@Service
public class Hasher implements IHasher {

    private static final Integer itteration = 20000;
    private static final Integer saltLen = 256;
    private static final Integer keyLen = 256;

    @Override
    public  String[] getSaltAndPassword (String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String[] saltAndPassword = new String[2];
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLen);
        saltAndPassword[0] = encoder.encodeToString(salt);
        saltAndPassword[1] = hash(password, salt);
        return saltAndPassword;
    }

    @Override
    public Boolean compareValues(String password, String stored, String salt) throws InvalidKeySpecException, NoSuchAlgorithmException {
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
