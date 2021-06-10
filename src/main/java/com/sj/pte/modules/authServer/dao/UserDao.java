package com.sj.pte.modules.authServer.dao;//package com.sj.pte.modules.authServer.dao;
//
///**
// * Created by TUTEHUB on 2021-05-31 16:46.
// * Copyright © 2021 TUTEHUB. All rights reserved.
// * ------------------------
// * Non-disclosure Terms
// * -------------------------
// * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
// * Developer fengpeng agrees with above terms.
// * Technique Support: jobyme88.com
// */
//
//import com.fengcao.webpte.bean.VocabularyBook;
//import com.fengcao.webpte.entity.JwtUser;
//import com.mongodb.client.result.UpdateResult;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.data.mongodb.core.query.Update;
//import org.springframework.stereotype.Component;
//
///**
// * @author pengffe  Email: pengffe@gmail.com
// * @descrption handle data of user
// * @date 27-05-2021
// */
//
//@Component
//public class UserDao {
//
//    @Autowired
//    private MongoTemplate mongoTemplate;
//
//    public boolean save(JwtUser user) {
//        if (null != user){
//                mongoTemplate.save(user);
//                //创建用户时直接自动生成用户练习表
////                UserPractice userPractice = new UserPractice();
////                userPractice.setUserId(user.getId());
////                userPractice.setRaAnswers(new ArrayList<>());
//
//                VocabularyBook vocabularyBook = new VocabularyBook();
//                vocabularyBook.setUserId(user.getId());
//                mongoTemplate.save(vocabularyBook);
//                return true;
//        }
//        else {
//            System.out.println("no instance to save");
//        }
//        return false;
//    }
//
//    public JwtUser findByUsername(String username){
//        Query query = new Query(Criteria.where("username").is(username));
//        return mongoTemplate.findOne(query, JwtUser.class);
//    }
//
//    public JwtUser findByEmail(String email){
//        Query query = new Query(Criteria.where("email").is(email));
//        return mongoTemplate.findOne(query, JwtUser.class);
//    }
//
//    public JwtUser findByPhone(String phone){
//        Query query = new Query(Criteria.where("phone").is(phone));
//        return mongoTemplate.findOne(query, JwtUser.class);
//    }
//
//    public UpdateResult resetPassword(String username, String password){
//        Query query = new Query(Criteria.where("username").is(username));
//        Update update = new Update().set("password", password);
//
//        return mongoTemplate.updateFirst(query, update, JwtUser.class);
//    }
//}
