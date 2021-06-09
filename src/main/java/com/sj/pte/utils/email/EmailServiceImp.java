package com.sj.pte.utils.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Created by TUTEHUB on 2021/6/2 10:20 PM.
 * Copyright © 2021 TUTEHUB. All rights reserved.
 * ------------------------
 * Non-disclosure Terms
 * -------------------------
 * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
 * Developer dph agrees with above terms.
 * Technique Support: jobyme88.com
 */


@Service
public class EmailServiceImp  {
    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender mailSender;

    public boolean sendEmail(String email, String username){
        System.out.println("send Email");

        String code = "123456";
        System.out.println("verify code:" + code);

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("[COW PTE] Verify your new account!");
        message.setText(String.format(
                "To verify your email address, please use the following One Time Password (OTP):\n" +
                        "%s \n"  +
                        "Do not share this OTP with anyone. Amazon takes your account security very seriously. CowPte Customer Service will never ask you to disclose or verify your CowPte password, OTP, credit card, or banking account number. If you receive a suspicious email with a link to update your account information, do not click on the link—instead, report the email to CowPte for investigation.\n" +
                        "Thank you!", code));
        message.setFrom(from);
        System.out.println("server: " + from);
        System.out.println("destination: " + email);

        try {
            mailSender.send(message);
        } catch (MailException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
