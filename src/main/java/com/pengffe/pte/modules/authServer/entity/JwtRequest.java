package com.pengffe.pte.modules.authServer.entity;

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

