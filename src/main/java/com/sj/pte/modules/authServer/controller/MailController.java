package com.sj.pte.modules.authServer.controller;/**
 * Created by TUTEHUB on 2021-06-02 16:37.
 * Copyright © 2021 TUTEHUB. All rights reserved.
 * ------------------------
 * Non-disclosure Terms
 * -------------------------
 * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
 * Developer fengpeng agrees with above terms.
 * Technique Support: jobyme88.com
 */

import com.sj.pte.modules.authServer.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author pengffe  Email: pengffe@gmail.com
 * @descrption verify mail
 */
@RestController
@RequestMapping("")
public class MailController {

    @Autowired
    private MailService mailService;

}
