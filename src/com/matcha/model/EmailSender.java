package com.matcha.model;

import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import java.util.Map;

@Service("emailSender")
@PropertySource("/WEB-INF/properties/mail.properties/")
public class EmailSender {

    @Value("${mail.from}")
    private String from;
    private JavaMailSender sender;
    private Configuration templateFactory;


    @Autowired
    public EmailSender(JavaMailSender sender, Configuration templateFactory) {
        this.sender = sender;
        this.templateFactory = templateFactory;
    }

    public void send(String templateName, String subject, String to, Map<String, Object> model)
    {
        sender.send(prepareMessage(templateName,subject, to, model));
    }


    private MimeMessagePreparator prepareMessage(String templateName,  String subject, String to, Map<String, Object> model)
    {

        return mimeMessage -> {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
            message.setFrom(from);
            message.setTo(to);
            message.setSubject(subject);
            String text = getContentFromTemplate(templateName, model);
            message.setText(text, true);
        };
    }

    private String getContentFromTemplate(String templateName, Map< String, Object > model) {
        StringBuilder content = new StringBuilder();

        try {
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(templateFactory.getTemplate(templateName), model));
        } catch (Exception e) {
           e.printStackTrace();
        }
        return content.toString();
    }
}