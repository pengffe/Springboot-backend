package com.pengffe.pte.modules.practice.dao;

import com.mongodb.client.result.UpdateResult;
import com.pengffe.pte.modules.practice.bean.UserCollectRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.pengffe.pte.utils.json.ClassFieldUtil.getFieldValueByName;

/**
 * @descrption
 */
@Component
public class UserCollectRecordDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public UserCollectRecord save(UserCollectRecord record) {
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
        UserCollectRecord userRecord = mongoTemplate.findOne(query, UserCollectRecord.class);
        return (List<String>) getFieldValueByName(type, userRecord);

    }

    public UpdateResult updateById(String userId, String questionId){
        Query query = new Query(Criteria.where("userId").is(userId));
        String key = questionId.split("-")[0];
        Update update = new Update().addToSet(key, questionId);

        return mongoTemplate.updateFirst(query, update, UserCollectRecord.class);
    }

    public UpdateResult deleteById(String userId, String questionId){
        Query query = new Query(Criteria.where("userId").is(userId));
        String key = questionId.split("-")[0];
        Update update = new Update().pull(key, questionId);

        return mongoTemplate.updateFirst(query, update, UserCollectRecord.class);
    }
}
