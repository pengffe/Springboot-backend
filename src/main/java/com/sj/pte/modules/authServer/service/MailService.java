package com.sj.pte.modules.authServer.service;

import cn.hutool.core.util.RandomUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.mongodb.client.result.UpdateResult;
import com.sj.pte.modules.authServer.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
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

    @Autowired
    private UserDao userDao;

    /**
     * Local Cache  3分钟过期
     */
    private Cache<String, String> localCache = CacheBuilder.newBuilder().maximumSize(1000).expireAfterAccess(2, TimeUnit.MINUTES).build();

    /**
     * html邮件
     * @param "to" 接收者邮件
     * @param "subject" 邮件主题
     * @param  "contnet" 邮件内容
     */
    public boolean sendVerifyEmail(String email) {
        System.out.println("send Email");

        String code = RandomUtil.randomNumbers(6);
        System.out.println("verify code:" + code);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(from);
            helper.setTo(email);
            helper.setSubject("Verify your new CowPte account!");

            helper.setText(String.format(
                "To verify your email address, please click the following link:\n" +
                "<a href=http://localhost:8081/api/v1/register/%s/%s>激活</a> \n"  +
                "Do not share this link with anyone. CowPte takes your account security very seriously. CowPte Customer Service will never ask you to disclose or verify your CowPte password, OTP, credit card, or banking account number. If you receive a suspicious email with a link to update your account information, do not click on the link—instead, report the email to CowPte for investigation.\n" +
                "Thank you!", email, code), true);

            mailSender.send(message);
            localCache.put(email, code);
            System.out.println("localCache: " + localCache.toString());
            return true;
        } catch (MailException e) {
            e.printStackTrace();
        } catch (MessagingException e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public boolean emailVerify(String email, String code) {

        String cacheCaptcha = localCache.getIfPresent(email);
        System.out.println("cacheCaptcha: " + cacheCaptcha);
        //删除验证码
        if (cacheCaptcha != null) {
            localCache.invalidate(email);
        }
        //效验成功
        if (code.equalsIgnoreCase(cacheCaptcha)) {
            System.out.println("校验成功！");
            return true;
        } else {
            System.out.println("校验失败！");
            return false;
        }
    }

    public boolean forgetPasswordEmail(String email) {
        System.out.println("Forget Password Email");

        String password = RandomUtil.randomNumbers(8);
        System.out.println("Temp password:" + password);

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("Reset your account password!");
        message.setText(String.format(
                "To reset your password, please use the following One Time Password to login:\n" +
                        "%s \n" +
                        "Do not share this OTP with anyone. CowPte takes your account security very seriously. CowPte Customer Service will never ask you to disclose or verify your CowPte password, OTP, credit card, or banking account number. If you receive a suspicious email with a link to update your account information, do not click on the link—instead, report the email to CowPte for investigation.\n" +
                        "Thank you!", password));
        message.setFrom(from);
        System.out.println("server: " + from);
        System.out.println("destination: " + email);

        try {
            mailSender.send(message);
            UpdateResult updateResult = userDao.resetPassword(userDao.findByEmail(email).getUsername(), password);
            if (updateResult.getMatchedCount() == 1 && updateResult.getModifiedCount() == 1){
                System.out.println("success reset password and send email");
                return true;
            }
            else {
                System.out.println("fail to reset password");
                return false;
            }
        } catch (MailException e) {
            e.printStackTrace();
            return false;
        } catch (NullPointerException e){
            System.out.println("Invalid email, fail to reset password");
            return false;
        }
    }
}

