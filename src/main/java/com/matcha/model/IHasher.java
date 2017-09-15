package com.matcha.model;


public interface IHasher {
    public  String[] getSaltAndPassword(String password);
    public  Boolean compareValues(String password, String stored, String salt);
}
