package com.sj.pte.modules.authServer.service;

import cn.hutool.core.util.RandomUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author pengffe  Email: pengffe@gmail.com
 * @descrption
 * @date
 */

@Service
public class MailService {

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Local Cache  3分钟过期
     */
    private Cache<String, String> localCache = CacheBuilder.newBuilder().maximumSize(1000).expireAfterAccess(2, TimeUnit.MINUTES).build();

    /**
     * 简单文本邮件
     * @param "to" 接收者邮件
     * @param "subject" 邮件主题
     * @param  "contnet" 邮件内容
     */
    public boolean sendEmail(String email){
        System.out.println("send Email");

        String code = RandomUtil.randomNumbers(6);
        System.out.println("verify code:" + code);

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("Verify your new CowPte account!");
        message.setText(String.format(
                "To verify your email address, please use the following One Time Password (OTP):\n" +
                "%s \n"  +
                "Do not share this OTP with anyone. CowPte takes your account security very seriously. CowPte Customer Service will never ask you to disclose or verify your CowPte password, OTP, credit card, or banking account number. If you receive a suspicious email with a link to update your account information, do not click on the link—instead, report the email to CowPte for investigation.\n" +
                "Thank you!", code));
        message.setFrom(from);
        System.out.println("server: " + from);
        System.out.println("destination: " + email);

        try {
            mailSender.send(message);
            localCache.put(email, code);
            System.out.println("localCache: " + localCache.toString());
        } catch (MailException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean emailVerify(String email, String code) {

        String cacheCaptcha = localCache.getIfPresent(email);
        System.out.println("cacheCaptcha: " + cacheCaptcha);
        //删除验证码
        if(cacheCaptcha != null){
            localCache.invalidate(email);
        }
        //效验成功
        if(code.equalsIgnoreCase(cacheCaptcha)){
            System.out.println("校验成功！");
            return true;
        }else {
            System.out.println("校验失败！");
            return false;
        }
    }
}

