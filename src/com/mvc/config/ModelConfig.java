package com.mvc.config;

import com.mvc.Entity.JsonResponse;
import com.mvc.Entity.JsonResponseWrapper;
import com.mvc.model.AuthorizationManager;
import com.mvc.model.Hasher;
import com.mvc.model.UserValidator;
import com.mvc.model.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Configuration
public class ModelConfig {

}
