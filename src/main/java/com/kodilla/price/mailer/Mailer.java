package com.kodilla.price.mailer;


import com.kodilla.price.entity.AmazonOffer;
import com.kodilla.price.entity.User;
import com.kodilla.price.scheduler.AmazonScheduler;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Data
public class Mailer {

    private final JavaMailSender javaMailSender;
    private final Logger logger = LoggerFactory.getLogger(AmazonScheduler.class);
    ;


    public void sendAlert(List<AmazonOffer> discountedOffers, Map<AmazonOffer, List<User>> offersForUsers) throws URISyntaxException, MessagingException, UnsupportedEncodingException {
        for (Map.Entry<AmazonOffer, List<User>> entry : offersForUsers.entrySet()) {
            AmazonOffer offer = entry.getKey();
            List<String> emails = entry.getValue().stream()
                    .map(User::getMail)
                    .collect(Collectors.toList());
            MimeMessage mailMessage = createMailMessage(offer, emails);
            javaMailSender.send(mailMessage);
        }

        logger.debug("Alert successfully sent");

    }

    private MimeMessage createMailMessage(AmazonOffer offer, List<String> emails) throws URISyntaxException, MessagingException, UnsupportedEncodingException {
        String alertLink = ("\n http://www.amazon.com/dp/" + offer.getAsin() + "/");

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        InternetHeaders headers = new InternetHeaders();
        headers.addHeader("Content-type", "text/html; charset= UTF-8");
        MimeBodyPart body = new MimeBodyPart();
        body.setText(alertLink, "UTF-8", "html");
        System.out.println(emails);
        helper.setFrom("AmazonAlerts@mail.com");
        helper.setBcc(emails.toString().replaceAll("\\[", "").replaceAll("\\]", ","));
        helper.setSubject("Discount of: " + offer.getProductName());
        helper.setText("Hey! " + offer.getProductName() + " is now cheaper " + alertLink);

        return message;
    }


}
