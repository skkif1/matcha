package com.mvc.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvc.DaoImpl.UserDaoImpl;
import com.mvc.Entity.JsonResponse;
import com.mvc.Entity.User;
import com.mvc.config.WebConfig;
import com.mvc.model.EmailSender;
import com.mvc.model.Hasher;
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

        Hasher hasher = new Hasher();

        boolean res = hasher.compareValues("password", "4Xi9Veya/NMxJUYVJAEyAdvEy3ye25b4X0BM0IUXLS8=", "cj9y7vHrr3oYhI0a3gd54cgWjedNntIWnq0SzTwTpRQL/blzV9rWC2w6tuhbictcMUamx26ZUUZYEWJXcO42BYVl6dWp+TGCEfn8/UiVCn2cOm6auv49D8gN8qOlWR5CP4A089Twi/QiamIhrOfTIGyJ7gVT3fCY0Lu+02pU48xqZhEyDYpM4b09Wnk7OW9WGZYZGvYp9zdRse1A9PE3KOtwfiWLHfwDKQQxNHV2u4UvhdGmJRC3NIohODPMca3mjOQ/HfUvfdDMbTOorBoyBUVV1YlhEqBEVI3T5MJL3KUHIV/mfgGQ3hqfUkzACgPwPuJUEdeZOcl6JK7UYmnRtA==");
        System.out.println(res);

    }
}
