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

    @GetMapping("/user")
    public Object getUser(HttpServletRequest request){
       return tokenParseService.tokenParser(request);
    }

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public ResponseEntity<?> userRegister(@RequestBody JwtRequest jwtRequest) {
        System.out.println("USER REGISTER");
        ResponseEntity<?> responseEntity = userService.userRegister(jwtRequest.getUsername(),
                jwtRequest.getPassword(), jwtRequest.getContact());

        if (responseEntity.getStatusCodeValue() == 200){
            String contact = jwtRequest.getContact();
            if (contact.contains("@")){
                mailService.sendEmail(contact);
            }
        }
        return responseEntity;
    }

    /**
     * 修改密码
     */
    @PutMapping("/user")
    public ResponseEntity<?> restPassword(HttpServletRequest request, @RequestBody JwtRequest jwtRequest){
        if (tokenParseService.tokenToUsername(request).equals(jwtRequest.getUsername())){
            System.out.println("RESET PASSWORD");
            return userService.restPassword(jwtRequest.getUsername(), jwtRequest.getPassword());
        }
        else
            return new ResponseEntity<>("Invalid token", HttpStatus.BAD_REQUEST);
    }

//    /**
//     * 忘记密码
//     */
//    @PutMapping("/forgetPassword")
//    public ResponseEntity<?> restPassword(HttpServletRequest request, @PathVariable String password){
//        System.out.println("RESET PASSWORD");
//        return userService.restPassword(tokenParseService.tokenToUsername(request), password);
//    }
}
