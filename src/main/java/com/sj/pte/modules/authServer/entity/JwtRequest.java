package com.sj.pte.modules.authServer.entity;
/**
 * Created by TUTEHUB on 2021-06-01 13:00.
 * Copyright © 2021 TUTEHUB. All rights reserved.
 * ------------------------
 * Non-disclosure Terms
 * -------------------------
 * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
 * Developer fengpeng agrees with above terms.
 * Technique Support: jobyme88.com
 */

/**
 * @author pengffe  Email: pengffe@gmail.com
 * @descrption spring security jwt
 */

import lombok.Data;

import java.io.Serializable;

@Data
public class JwtRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private String contact;
    private String password;
}

