package com.ai.evaira_backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private static final Logger log = LoggerFactory.getLogger(EmailService.class);
    @Autowired
    private JavaMailSender mailSender;


    public void sendOtp(String to, String otp) {
        log.info("Sending OTP to {}", to);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your Evaira OTP Code");

        String text = "Hi,\n\n"
                + "Your Evaira verification code is: " + otp + "\n\n"
                + "This code is valid for the next 10 minutes. "
                + "Do not share it with anyone.\n\n"
                + "If you didn’t request this code, you can safely ignore this email.";

        message.setText(text);
        mailSender.send(message);
    }

}

