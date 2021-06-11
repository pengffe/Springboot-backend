package com.sj.pte.modules.comment;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author pengffe  Email: pengffe@gmail.com
 * @descrption handle comments for each question
 * @date 26-05-2021
 */

@Component
public class MNCommentDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public MNComment addComment(MNComment comment){
        return mongoTemplate.save(comment);
    }

    public List<MNComment> findAllByQuestionId(String id){
        Query query = new Query(Criteria.where("questionId").is(id));
        return mongoTemplate.find(query,MNComment.class);
    }

    public DeleteResult deleteById(Long id){
        Query query = new Query(Criteria.where("id").is(id));
        return mongoTemplate.remove(query,MNComment.class);
    }

    public UpdateResult updateMessage(String id, String message){
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update().set("message", message);

        return mongoTemplate.updateFirst(query, update, MNComment.class);
    }
}
