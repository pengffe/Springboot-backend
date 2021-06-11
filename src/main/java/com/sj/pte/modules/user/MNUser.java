package com.sj.pte.modules.user;

import com.sj.pte.modules.practice.bean.MNPractice;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.List;

/**
 * Created by TUTEHUB on 2021/6/1 10:33 AM.
 * Copyright © 2021 TUTEHUB. All rights reserved.
 * ------------------------
 * Non-disclosure Terms
 * -------------------------
 * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
 * Developer dph agrees with above terms.
 * Technique Support: jobyme88.com
 */


@Document(collection = "user")
public class MNUser {
    @Id
    private Long id;

    /****************************************
     * User Register and Contact Information
     *****************************************/
    private String userEmail;
    private String userName;
    private String userPhone;
    private MNAddress address;
    /**
     * @description MD5 encrypted stored in database
     * Wordpress would change password everytime the user log in
     */
    private String userPass;
    private String userWechat;
    /**
     * @description image path
     */
    private String userAvator;


    /****************************************
     * User Practice Question
     *****************************************/
    private List<String> practicedQuestions;
    /**
     * @description There are a few colors of collect
     */
    private HashMap<CollectTypeEnum, String> collectedQuestions;
    /**
     * @description Only record speak and write
     */
    private List<MNPractice> practices;



    /**
     * user levels to stimulate students
     */
    private int userLevel;

    private UserTypeEnum userType;

}
