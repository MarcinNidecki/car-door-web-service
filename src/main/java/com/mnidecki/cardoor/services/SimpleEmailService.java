package com.mnidecki.cardoor.services;

import com.mnidecki.cardoor.domain.Mail;
import com.mnidecki.cardoor.domain.booking.Booking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class SimpleEmailService {

    private static Logger LOGGER = LoggerFactory.getLogger(MimeMessagePreparator.class);

    @Autowired
    private JavaMailSender javaMailSender;


    public void send(Mail mail) {
        LOGGER.info("Starting emial preparation...");
        try {
            javaMailSender.send(createMailMessage(mail));

            LOGGER.info("Email has been sent.");
        } catch (MailException e) {
            LOGGER.error("Failed to process email sending: ", e.getMessage(), e);
        }
    }

    public boolean sendBookingConfirmation(Booking booking) {
        Mail mail = new Mail(
                booking.getUser().getEmail(),
                "CARDOOR car reservation confirmation.",
                "CARDOOR car reservation confirmation");
        LOGGER.info("Starting emial preparation...");

        try {
            javaMailSender.send(createMailMessage(mail));
            LOGGER.info("Email has been sent.");
            return true;


        } catch (MailException e) {
            LOGGER.error("Failed to process email sending: ", e.getMessage(), e);
            return false;
        }
    }

    private SimpleMailMessage createMailMessage(Mail mail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        if ((mail.getToCc() != null)) {
            mailMessage.setCc(mail.getToCc());
        }
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        return mailMessage;
    }
}
