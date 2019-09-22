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
import org.springframework.stereotype.Service;

@Service
public class SimpleEmailService {
    private static  Logger LOGGER = LoggerFactory.getLogger(MimeMessagePreparator.class);

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private MailCreatorService mailCreatorService;

    public void send(Mail mail, Booking booking) {
        LOGGER.info("Starting emial preparation...");
        try {
                javaMailSender.send(bookingConfirmationMail(mail,booking));

            LOGGER.info("Email has been sent.");
        }catch (MailException e) {
            LOGGER.error("Failed to process email sending: ", e.getMessage(),e);
        }
    }
    public void send(Mail mail) {
        LOGGER.info("Starting emial preparation...");
        try {
            javaMailSender.send(createMailMessage(mail));

            LOGGER.info("Email has been sent.");
        }catch (MailException e) {
            LOGGER.error("Failed to process email sending: ", e.getMessage(),e);
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

    private MimeMessagePreparator bookingConfirmationMail(final Mail mail, Booking booking) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mailCreatorService.bookingConfirmation(booking),true);
        };
    }

}
