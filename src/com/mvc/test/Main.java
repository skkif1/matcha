package com.mvc.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mvc.Entity.InformationBoundledUser;
import com.mvc.Entity.User;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException, InvalidKeySpecException, NoSuchAlgorithmException, InterruptedException, JsonProcessingException {

        ArrayList<String> user = new ArrayList<>();

        user.add("1");
        user.add("2");
        user.add("3");

        System.out.println(user.toString());
    }
}
