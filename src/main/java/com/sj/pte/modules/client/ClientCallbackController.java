package com.sj.pte.modules.client;/**
 * Created by TUTEHUB on 2021-06-10 13:49.
 * Copyright © 2021 TUTEHUB. All rights reserved.
 * ------------------------
 * Non-disclosure Terms
 * -------------------------
 * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
 * Developer fengpeng agrees with above terms.
 * Technique Support: jobyme88.com
 */

/**
 * @descrption
 */


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class ClientCallbackController {

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/client/account/redirect")
    public String getToken(@RequestParam String code) {
        log.info("receive code {}", code);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("code", code);
        params.add("client_id", "client1");
        params.add("client_secret", "secret");
        params.add("redirect_uri", "http://localhost:8081/api/v1/client/account/redirect");
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8081/api/v1/oauth/token", requestEntity, String.class);
        String token = response.getBody();
        System.out.println("token: " + token);
        log.info("token => {}", token);
        return token;
    }
}

//http://localhost:8081/api/v1/oauth/authorize?client_id=client1&response_type=code&redirect_uri=http://localhost:8081/api/v1/client/account/redirect
