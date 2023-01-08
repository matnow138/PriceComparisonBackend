package com.kodilla.price.mailer;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Slf4j
@RequiredArgsConstructor
@Service
public class Mailer {

    private final JavaMailSender javaMailSender;

    private Properties prop = new Properties();
    public void config(){

        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.mailtrap.io");
        prop.put("mail.smtp.port", "25");
        prop.put("mail.smtp.ssl.trust", "smtp.mailtrap.io");

    }

    public Session newSession(){
        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("29a74df91e7b05", "08516cfc35c319");
            }
        });
        return session;
    }

    public SimpleMailMessage sendAlert() throws MessagingException {
        Message message = new MimeMessage(newSession());
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo("test");
        mailMessage.setSubject("test Subject");
        mailMessage.setText("test text");

        javaMailSender.send(mailMessage);
        return mailMessage;
    }


}
