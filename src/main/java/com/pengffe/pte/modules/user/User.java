package com.pengffe.pte.modules.user;

import com.pengffe.pte.modules.practice.bean.Practice;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.List;


@Document(collection = "user")
public class User {
    @Id
    private Long id;

    /****************************************
     * User Register and Contact Information
     *****************************************/
    private String userEmail;
    private String userName;
    private String userPhone;
    private Address address;
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
    private List<Practice> practices;



    /**
     * user levels to stimulate students
     */
    private int userLevel;

    private UserTypeEnum userType;

}
