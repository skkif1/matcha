package com.mvc.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mvc.DaoImplMysql.UserInformationDaoImpl;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException, InvalidKeySpecException, NoSuchAlgorithmException, InterruptedException, JsonProcessingException {

        ArrayList<String> interests = new ArrayList<>();

        interests.add("1");
        interests.add("2");
        interests.add("3");


        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/matcha?useSSL=false");
        dataSource.setUsername("root");
        dataSource.setPassword("password");


        UserInformationDaoImpl dao = new UserInformationDaoImpl(dataSource);

    }
}
