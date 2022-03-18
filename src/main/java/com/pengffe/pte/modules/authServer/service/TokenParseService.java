package com.pengffe.pte.modules.authServer.service;

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
