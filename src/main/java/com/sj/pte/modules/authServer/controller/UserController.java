package com.sj.pte.modules.authServer.controller;/**
 * Created by TUTEHUB on 2021-06-14 22:18.
 * Copyright © 2021 TUTEHUB. All rights reserved.
 * ------------------------
 * Non-disclosure Terms
 * -------------------------
 * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
 * Developer fengpeng agrees with above terms.
 * Technique Support: jobyme88.com
 */

import com.sj.pte.modules.authServer.entity.JwtRequest;
import com.sj.pte.modules.authServer.service.MailService;
import com.sj.pte.modules.authServer.service.TokenParseService;
import com.sj.pte.modules.authServer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

/**
 * @descrption
 */

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Autowired
    private TokenParseService tokenParseService;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public ResponseEntity<?> userRegister(@RequestBody JwtRequest jwtRequest) throws MessagingException {
        System.out.println("USER REGISTER");
        ResponseEntity<?> responseEntity = userService.userRegister(jwtRequest.getUsername(),
                jwtRequest.getPassword(), jwtRequest.getContact());

        if (responseEntity.getStatusCodeValue() == 200){
            String contact = jwtRequest.getContact();
            if (contact.contains("@")){
                mailService.sendVerifyEmail(contact);
            }
        }
        return responseEntity;
    }

    /**
     * 重新发送邮件
     */
    @PostMapping("register/{email}")
    public boolean resendEmail(@PathVariable String email){
        return mailService.sendVerifyEmail(email);
    }

    /**
     * 邮箱或者手机验证
     */
    @GetMapping("/register/{email}/{code}")
    public ResponseEntity<?> emailVerify(@PathVariable String email, @PathVariable String code){
        if (mailService.emailVerify(email, code)){
            if (userService.activateUser(email)){
                return new ResponseEntity<>("Account has been activated",HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>("Fail to activate user", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else {
            return  new ResponseEntity<>("Invalid code or expired", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * 忘记密码
     */
    @PutMapping("/register/{identifier}")
    public ResponseEntity<?> restPassword(@PathVariable String identifier){
        System.out.println("FORGET PASSWORD");
        String email;
        if (identifier.contains("@")){
            email = identifier;
        }
        else {
            email = userService.findEmailByUsername(identifier);
        }
        if (mailService.forgetPasswordEmail(email)){
            return new ResponseEntity<>("Success reset password, please check your email!", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Fail to reset password", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 获取用户信息
     */
    @GetMapping("/user")
    public Object getUser(HttpServletRequest request){
        return tokenParseService.tokenParser(request);
    }

    /**
     * 修改密码
     */
    @PutMapping("/user")
    public ResponseEntity<?> restPassword(HttpServletRequest request, @RequestBody JwtRequest jwtRequest){
        //check whether the username parsed from token and the request username is identical
        if (tokenParseService.tokenToUsername(request).equals(jwtRequest.getUsername())){
            System.out.println("RESET PASSWORD");
            return userService.restPassword(jwtRequest.getUsername(), jwtRequest.getPassword());
        }
        else
            return new ResponseEntity<>("Invalid token", HttpStatus.BAD_REQUEST);
    }
}
