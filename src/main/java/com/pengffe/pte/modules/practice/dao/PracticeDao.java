package com.pengffe.pte.modules.practice.dao;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.pengffe.pte.modules.practice.bean.Practice;
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

    public Practice save(Practice practiceRecord) {
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

        return mongoTemplate.updateFirst(query, update, Practice.class);
    }

    public List<Practice> findByQuestionIdForUser(String userId, String questionId){
        Query query = new Query();
        query.addCriteria(Criteria.where("questionId").is(questionId).and("userId").is(userId));

        return mongoTemplate.find(query, Practice.class);
    }

    public List findByQuestionId(String questionId){
        Query query = new Query(Criteria.where("questionId").is(questionId));
        return mongoTemplate.find(query, Practice.class);
    }

    public List findByUserIdForType(String userId, String type){
        Query query = new Query(Criteria.where("userId").is(userId));
        List<Practice> mnPractices = mongoTemplate.find(query, Practice.class);
        List<Practice> mnPracticeListForOneType = new ArrayList<>();

        for (Practice practice: mnPractices
             ) {
            if (practice.getQuestionId().contains(type)){
                mnPracticeListForOneType.add(practice);
            }

        }
        return mnPracticeListForOneType;
    }

    public DeleteResult deleteById(long id) {
        Query query = new Query(Criteria.where("id").is(id));
        return mongoTemplate.remove(query, Practice.class);
    }

    public <T> T findById(Class<T> tCLass, String userId) {
        Query query = new Query(Criteria.where("userId").is(userId));
        return mongoTemplate.findOne(query, tCLass);
    }
}
