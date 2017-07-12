package com.matcha.model;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface IHasher {

    public  String[] getSaltAndPassword(String password);
    public  Boolean compareValues(String password, String stored, String salt);
}
