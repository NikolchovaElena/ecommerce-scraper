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
        String message = String.format(
                "Vendor: %s%n " +
                        "has changed %s price%n" +
                        "to %s",
                event.getCompetitor().getUrl(),
                event.getCompetitor().getProduct().getName(),
                (event.getNewPrice() + " " + event.getCurrency()));

        emailService.findAll().forEach(e -> {
            email.setTo(e.getEmail());
            email.setSubject("Product Price has changed");
            email.setText(message);
            mailSender.send(email);
        });
    }
}
