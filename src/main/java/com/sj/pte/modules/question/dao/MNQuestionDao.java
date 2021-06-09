package com.sj.pte.modules.question.dao;/**
 * Created by TUTEHUB on 2021/6/3 9:30 PM.
 * Copyright © 2021 TUTEHUB. All rights reserved.
 * ------------------------
 * Non-disclosure Terms
 * -------------------------
 * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
 * Developer dph agrees with above terms.
 * Technique Support: jobyme88.com
 * Contact: noreply@fengcaoculture.com
 */


import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @description
 */
@Component
public class MNQuestionDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    /*********
     * Create
     *********/
    public <T> T save(T t) {
        if (null != t){
            return mongoTemplate.save(t);
        }
        else {
            System.out.println("no instance to save");
            return null;
        }
    }


    public <T> T findById(Class<T> tCLass, String id) {
        Query query = new Query(Criteria.where("questionId").is(id));
        return mongoTemplate.findOne(query, tCLass);
    }

    public <T> List<T> findAll(Class<T> tClass) {
        return mongoTemplate.findAll(tClass);
    }



    public <T> DeleteResult deleteById(Class<T> tCLass, String id) {
        Query query = new Query(Criteria.where("questionId").is(id));
        return mongoTemplate.remove(query, tCLass);
    }

    public <T> UpdateResult updateImageById(Class<T> tCLass, String questionId, String imagePath){
        Query query = new Query(Criteria.where("questionId").is(questionId));
        Update update = new Update().set("imagePath", imagePath);

        return mongoTemplate.updateFirst(query, update, tCLass);
    }

    public <T> UpdateResult updateAudioById(Class<T> tCLass, String questionId, String audioPath){
        Query query = new Query(Criteria.where("questionId").is(questionId));
        Update update = new Update().set("audioPath", audioPath);

        return mongoTemplate.updateFirst(query, update, tCLass);
    }

    public <T> UpdateResult updateFrequencyById(Class<T> tCLass, String id, int frequency){
        Query query = new Query(Criteria.where("questionId").is(id));
        Update update = new Update().set("frequency", frequency);

        return mongoTemplate.updateFirst(query, update, tCLass);
    }

    public <T> UpdateResult updateIsUpdatedById(Class<T> tCLass,  String id, boolean isUpdated){
        Query query = new Query(Criteria.where("questionId").is(id));
        Update update = new Update().set("update", isUpdated);
        // [Shark] what does this sentence mean
        update.set("updateTime", new Date());

        return mongoTemplate.updateFirst(query, update, tCLass);
    }
}
