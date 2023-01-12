package com.kodilla.price.mailer;


import com.kodilla.price.entity.AmazonOffer;
import com.kodilla.price.service.AmazonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Slf4j
@RequiredArgsConstructor
@Service
public class Mailer {

    private final JavaMailSender javaMailSender;
    private final AmazonService amazonService;




    public void sendAlert(List<AmazonOffer> discountedOffers) throws URISyntaxException {
        SimpleMailMessage mailMessage = createMailMessage(discountedOffers);
        javaMailSender.send(mailMessage);
        System.out.println("Alert sent!");
    }

    private SimpleMailMessage createMailMessage(List<AmazonOffer> discountedOffers) throws URISyntaxException {
        List<String> discounts = new ArrayList<>();
        for(AmazonOffer amazon: discountedOffers){
            discounts.add(amazon.getAsin());
        }
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo("test");
        mailMessage.setSubject("NEW DISCOUNT!");
        mailMessage.setText(discounts.toString());
        return  mailMessage;
    }



}
