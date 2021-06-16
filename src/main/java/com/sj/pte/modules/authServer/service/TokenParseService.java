package com.sj.pte.modules.authServer.service;/**
 * Created by TUTEHUB on 2021-06-16 17:18.
 * Copyright © 2021 TUTEHUB. All rights reserved.
 * ------------------------
 * Non-disclosure Terms
 * -------------------------
 * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
 * Developer fengpeng agrees with above terms.
 * Technique Support: jobyme88.com
 */

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

/**
 * @descrption
 */

@Service
public class TokenParseService {

    public Object tokenParser(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        String token = header.substring(header.lastIndexOf("bearer") + 7);
        return Jwts.parser()
                .setSigningKey("webpte".getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody();
    }

    public String tokenToUsername(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        String token = header.substring(header.lastIndexOf("bearer") + 7);
        return Jwts.parser()
                .setSigningKey("webpte".getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody().get("user_name").toString();
    }
}
