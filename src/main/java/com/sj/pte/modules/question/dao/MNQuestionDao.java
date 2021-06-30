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
import com.sj.pte.modules.question.QuestionLevelEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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

    public <T> Page<T> findAllByPage(Class<T> tClass, int pageNum, int pageSize, String sortType) {
        Sort sort;
        if (sortType.equals("ASC")){
             sort = Sort.by(Sort.Direction.ASC, "id");
        }
        else {
            sort = Sort.by(Sort.Direction.DESC, "frequency");
        }

        Query query = new Query();
        long count = mongoTemplate.count(query, tClass);
        if (0 == pageSize){
            pageSize = (int) count;
        }
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);


        //查询结果集
        List<T> questionList= mongoTemplate.find(query.with(pageable), tClass);
        Page<T> questionPage = new PageImpl(questionList, pageable, count);
        return questionPage;
    }

    public <T> Long findCount(Class<T> tClass){
        Query query = new Query();
        return mongoTemplate.count(query, tClass);
    }

    public <T> DeleteResult deleteById(Class<T> tCLass, String id) {
        Query query = new Query(Criteria.where("questionId").is(id));
        return mongoTemplate.remove(query, tCLass);
    }

    /**
     * UPDATE ATTRIBUTES
     */
    public <T> UpdateResult updateTitleById(Class<T> tCLass, String questionId, String title){
        Query query = new Query(Criteria.where("questionId").is(questionId));
        Update update = new Update().set("title", title);

        return mongoTemplate.updateFirst(query, update, tCLass);
    }

    public <T> UpdateResult updateContentById(Class<T> tCLass, String questionId, String content){
        Query query = new Query(Criteria.where("questionId").is(questionId));
        Update update = new Update().set("content", content);

        return mongoTemplate.updateFirst(query, update, tCLass);
    }

    public <T> UpdateResult updateVerifiedById(Class<T> tCLass, String questionId, boolean verified){
        Query query = new Query(Criteria.where("questionId").is(questionId));
        Update update = new Update().set("verified", verified);

        return mongoTemplate.updateFirst(query, update, tCLass);
    }

    public <T> UpdateResult updatePredictedById(Class<T> tCLass, String questionId, boolean predicted){
        Query query = new Query(Criteria.where("questionId").is(questionId));
        Update update = new Update().set("predicted", predicted);

        return mongoTemplate.updateFirst(query, update, tCLass);
    }

    public <T> UpdateResult updateNewById(Class<T> tCLass, String questionId, boolean add){
        Query query = new Query(Criteria.where("questionId").is(questionId));
        Update update = new Update().set("add", add);

        return mongoTemplate.updateFirst(query, update, tCLass);
    }

    public <T> UpdateResult updateUpdatedById(Class<T> tCLass, String questionId, boolean updated){
        Query query = new Query(Criteria.where("questionId").is(questionId));
        Update update = new Update().set("updated", updated);

        return mongoTemplate.updateFirst(query, update, tCLass);
    }

    public <T> UpdateResult updateFrequencyById(Class<T> tCLass, String id, int frequency){
        Query query = new Query(Criteria.where("questionId").is(id));
        Update update = new Update().set("frequency", frequency);

        return mongoTemplate.updateFirst(query, update, tCLass);
    }

    public <T> UpdateResult updateLevelById(Class<T> tCLass, String id, QuestionLevelEnum level){
        Query query = new Query(Criteria.where("questionId").is(id));
        Update update = new Update().set("level", level);

        return mongoTemplate.updateFirst(query, update, tCLass);
    }

    public <T> UpdateResult updateTestDateById(Class<T> tCLass, String id, Date testedDates){
        Query query = new Query(Criteria.where("questionId").is(id));
        Update update = new Update().addToSet("testedDates", testedDates);

        return mongoTemplate.updateFirst(query, update, tCLass);
    }

    public <T> UpdateResult updateSourceById(Class<T> tCLass, String questionId, String source){
        Query query = new Query(Criteria.where("questionId").is(questionId));
        Update update = new Update().set("source", source);

        return mongoTemplate.updateFirst(query, update, tCLass);
    }

    public <T> UpdateResult updateLessonById(Class<T> tCLass, String questionId, String filePath){
        Query query = new Query(Criteria.where("questionId").is(questionId));
        Update update = new Update().set("lessonPath", filePath);

        return mongoTemplate.updateFirst(query, update, tCLass);
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

    public <T> UpdateResult updateOptionsById(Class<T> tCLass, String questionId, List<String> options){
        Query query = new Query(Criteria.where("questionId").is(questionId));
        Update update = new Update().set("options", options);

        return mongoTemplate.updateFirst(query, update, tCLass);
    }

    public <T> UpdateResult updateQuestionById(Class<T> tCLass, String questionId, String question){
        Query query = new Query(Criteria.where("questionId").is(questionId));
        Update update = new Update().set("question", question);

        return mongoTemplate.updateFirst(query, update, tCLass);
    }

    public <T> UpdateResult updateModifiedDateById(Class<T> tCLass,  String id){
        Query query = new Query(Criteria.where("questionId").is(id));
        Update update = new Update().set("update", true);
        update.set("modifiedDate", new Date());

        return mongoTemplate.updateFirst(query, update, tCLass);
    }
}
