package com.mvc.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvc.DaoImpl.UserDaoImpl;
import com.mvc.Entity.JsonResponse;
import com.mvc.Entity.User;
import com.mvc.config.WebConfig;
import com.mvc.model.EmailSender;
import com.mvc.model.UserValidator;
import com.sun.deploy.util.ArrayUtil;
import com.sun.tools.javac.util.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException, InvalidKeySpecException, NoSuchAlgorithmException, InterruptedException, JsonProcessingException {

//
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//        dataSource.setUrl("jdbc:mysql://localhost:3306/matcha");
//        dataSource.setUsername("root");
//        dataSource.setPassword("75g03f24");
//
//
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost("smtp.gmail.com");
//        mailSender.setPort(587);
//        mailSender.setUsername("matchamefast@gmail.com");
//        mailSender.setPassword("75g03f24");
//
//        Properties javaMailProperties = new Properties();
//        javaMailProperties.put("mail.smtp.starttls.enable", "true");
//        javaMailProperties.put("mail.smtp.auth", "true");
//        javaMailProperties.put("mail.transport.protocol", "smtp");
//        javaMailProperties.put("mail.debug", "true");//Prints out everything on screen
//
//        mailSender.setJavaMailProperties(javaMailProperties);
//
//        MimeMessagePreparator preparator = new MimeMessagePreparator() {
//            @Override
//            public void prepare(MimeMessage mimeMessage) throws Exception {
//                mimeMessage.setFrom(new InternetAddress("matchamefast@gmail.com"));
//                mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress("skkif1@gmail.com"));
//                mimeMessage.setText("Hello");
//                mimeMessage.setSubject("Hello eodfkspo");
//            }
//        };
//
//        mailSender.send(preparator);
//    }


//        AbstractApplicationContext context = new AnnotationConfigApplicationContext(WebConfig.class);
//
//        EmailSender orderService = (EmailSender) context.getBean("emailSender");
//        orderService.send("sad", "asd", "asd");
//        ((AbstractApplicationContext) context).close();
    }
}
