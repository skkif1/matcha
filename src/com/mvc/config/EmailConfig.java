package com.mvc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@PropertySource("/WEB-INF/properties/mail.properties/")
public class EmailConfig {

    @Value("${mail.server.name}")
    private String name;

    @Value("${mail.server.password}")
    private String password;

    @Value("${mail.server.smtp}")
    private String host;

    @Value("${mail.server.port}")
    private Integer port;

    @Bean(name = "getMailSender")
    public JavaMailSenderImpl getMailSender()
    {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setUsername(name);
        sender.setPassword(password);
        sender.setPort(port);
        sender.setHost(host);
        sender.setJavaMailProperties(getProperties());
        return  sender;
    }



    private Properties getProperties()
    {
        Properties prop = new Properties();
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.smtp.auth", "true");
        prop.setProperty("mail.smtp.starttls.required", "true");
        prop.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.put("mail.debug", "false");
        return prop;
    }



}
