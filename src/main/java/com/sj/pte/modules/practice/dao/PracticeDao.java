package com.sj.pte.modules.practice.dao;

/**
 * Created by TUTEHUB on 2021-05-31 16:46.
 * Copyright © 2021 TUTEHUB. All rights reserved.
 * ------------------------
 * Non-disclosure Terms
 * -------------------------
 * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
 * Developer fengpeng agrees with above terms.
 * Technique Support: jobyme88.com
 */

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.sj.pte.modules.practice.bean.MNPractice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pengffe  Email: pengffe@gmail.com
 * @descrption update user's practice
 * @date 28-05-2021
 */

@Component
public class PracticeDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public MNPractice save(MNPractice practiceRecord) {
        if (null != practiceRecord){
            return mongoTemplate.save(practiceRecord);
        }
        else {
            System.out.println("no instance to save");
            return null;
        }
    }

    public UpdateResult updateById(Long id, String answer){
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update().set("answer", answer);

        return mongoTemplate.updateFirst(query, update, MNPractice.class);
    }

    public List<MNPractice> findByQuestionIdForUser(String userId, String questionId){
        Query query = new Query();
        query.addCriteria(Criteria.where("questionId").is(questionId).and("userId").is(userId));

        return mongoTemplate.find(query,MNPractice.class);
    }

    public List findByQuestionId(String questionId){
        Query query = new Query(Criteria.where("questionId").is(questionId));
        return mongoTemplate.find(query, MNPractice.class);
    }

    public List findByUserIdForType(String userId, String type){
        Query query = new Query(Criteria.where("userId").is(userId));
        List<MNPractice> mnPractices = mongoTemplate.find(query, MNPractice.class);
        List<MNPractice> mnPracticeListForOneType = new ArrayList<>();

        for (MNPractice practice: mnPractices
             ) {
            if (practice.getQuestionId().contains(type)){
                mnPracticeListForOneType.add(practice);
            }

        }
        return mnPracticeListForOneType;
    }

    public DeleteResult deleteById(long id) {
        Query query = new Query(Criteria.where("id").is(id));
        return mongoTemplate.remove(query, MNPractice.class);
    }

    public <T> T findById(Class<T> tCLass, String userId) {
        Query query = new Query(Criteria.where("userId").is(userId));
        return mongoTemplate.findOne(query, tCLass);
    }
}
