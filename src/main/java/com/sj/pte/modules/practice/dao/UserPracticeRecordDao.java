package com.sj.pte.modules.practice.dao;/**
 * Created by TUTEHUB on 2021-07-06 12:32.
 * Copyright © 2021 TUTEHUB. All rights reserved.
 * ------------------------
 * Non-disclosure Terms
 * -------------------------
 * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
 * Developer fengpeng agrees with above terms.
 * Technique Support: jobyme88.com
 */

import com.mongodb.client.result.UpdateResult;
import com.sj.pte.modules.practice.bean.MNUserCollectRecord;
import com.sj.pte.modules.practice.bean.MNUserPracticeRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.sj.pte.utils.json.ClassFieldUtil.getFieldValueByName;

/**
 * @descrption
 */

@Component
public class UserPracticeRecordDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public MNUserPracticeRecord save(MNUserPracticeRecord record) {
        if (null != record){
            return mongoTemplate.save(record);
        }
        else {
            System.out.println("no instance to save");
            return null;
        }
    }

    public List<String> findAllByTypeForUser(String userId, String type){
        Query query = new Query(Criteria.where("userId").is(userId));
        MNUserPracticeRecord userRecord = mongoTemplate.findOne(query, MNUserPracticeRecord.class);
        return (List<String>) getFieldValueByName(type, userRecord);

    }

    public UpdateResult updateById(String userId, String questionId){
        Query query = new Query(Criteria.where("userId").is(userId));
        String key = questionId.split("-")[0];
        MNUserPracticeRecord userRecord = mongoTemplate.findOne(query, MNUserPracticeRecord.class);
        List<String> questionIdList = (List<String>) getFieldValueByName(key, userRecord);
        Update update = new Update();
        if (null == questionIdList){
            update = update.addToSet(key, questionId);
        }
        else if (!questionIdList.contains(questionId)){
           update = update.addToSet(key, questionId);
        }
        return mongoTemplate.updateFirst(query, update, MNUserPracticeRecord.class);
    }

    public UpdateResult deleteById(String userId, String questionId){
        Query query = new Query(Criteria.where("userId").is(userId));
        String key = questionId.split("-")[0];
        Update update = new Update().pull(key, questionId);

        return mongoTemplate.updateFirst(query, update, MNUserPracticeRecord.class);
    }

}
