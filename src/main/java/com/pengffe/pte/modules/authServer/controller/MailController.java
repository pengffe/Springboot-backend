package com.pengffe.pte.modules.authServer.controller;

import com.pengffe.pte.modules.authServer.service.MailService;
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
