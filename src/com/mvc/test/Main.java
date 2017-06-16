package com.mvc.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mvc.DAO.PhotoDao;
import com.mvc.DaoImplMysql.UserInformationDaoImpl;
import com.mvc.DaoImplPhoto.PhotoDaoImpl;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException, InvalidKeySpecException, NoSuchAlgorithmException, InterruptedException, IOException {

        ArrayList<String> interests = new ArrayList<>();

        interests.add("1");
        interests.add("2");
        interests.add("3");


        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/matcha?useSSL=false");
        dataSource.setUsername("root");
        dataSource.setPassword("password");

        PhotoDao dao = new PhotoDaoImpl();

        BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File("/nfs/2016/o/omotyliu/Desktop/Aleksandr_Motyliuk.pdf.PDF")));

        byte [] file = new byte[in.available()];

        in.read(file, 0, in.available());

    }
}
