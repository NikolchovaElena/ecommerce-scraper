package com.demo.scraper.service.listeners;

import com.demo.scraper.repository.EmailService;
import com.demo.scraper.service.events.OnLowerPriceUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class PriceChangeListener implements ApplicationListener<OnLowerPriceUpdate> {
    private static final String EMAIL_MESSAGE =
            "Product price has changed for" +
            "Vendor: %s%n" +
            "Product: %s%n" +
            "New Price: %s";
    private static final String EMAIL_SUBJECT ="Product Price has changed";

    private MailSender mailSender;
    private EmailService emailService;

    @Autowired
    public PriceChangeListener(MailSender mailSender, EmailService emailService) {
        this.mailSender = mailSender;
        this.emailService = emailService;
    }

    @Override
    public void onApplicationEvent(OnLowerPriceUpdate event) {
        this.sendEmail(event);
    }

    private void sendEmail(OnLowerPriceUpdate event) {
        SimpleMailMessage email = new SimpleMailMessage();
        String message = String.format(EMAIL_MESSAGE,
                event.getCompetitor().getUrl(),
                event.getCompetitor().getProduct().getName(),
                (event.getNewPrice() + " " + event.getCurrency()));

        emailService.findAll().forEach(e -> {
            email.setTo(e.getEmail());
            email.setSubject(EMAIL_SUBJECT);
            email.setText(message);
            mailSender.send(email);
        });
    }
}
