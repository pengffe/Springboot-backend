package com.pengffe.pte.modules.practice.dao;

import com.mongodb.client.result.UpdateResult;
import com.pengffe.pte.modules.practice.bean.UserPracticeRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.pengffe.pte.utils.json.ClassFieldUtil.getFieldValueByName;

/**
 * @descrption
 */

@Component
public class UserPracticeRecordDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public UserPracticeRecord save(UserPracticeRecord record) {
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
        UserPracticeRecord userRecord = mongoTemplate.findOne(query, UserPracticeRecord.class);
        return (List<String>) getFieldValueByName(type, userRecord);

    }

    public UpdateResult updateById(String userId, String questionId){
        Query query = new Query(Criteria.where("userId").is(userId));
        String key = questionId.split("-")[0];
        UserPracticeRecord userRecord = mongoTemplate.findOne(query, UserPracticeRecord.class);
        List<String> questionIdList = (List<String>) getFieldValueByName(key, userRecord);
        Update update = new Update();
        if (null == questionIdList){
            update = update.addToSet(key, questionId);
        }
        else if (!questionIdList.contains(questionId)){
           update = update.addToSet(key, questionId);
        }
        else {
            return null;
        }
        return mongoTemplate.updateFirst(query, update, UserPracticeRecord.class);
    }

    public UpdateResult resetByUserId(String userId){
        Query query = new Query(Criteria.where("userId").is(userId));
        Update update = new Update();

        Class<?> clazz = UserPracticeRecord.class;
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field item: declaredFields
             ) {
            String key = item.getName();
            if (key.equals("userId")) continue;
            update.set(key, new ArrayList<>());
        }

        return mongoTemplate.updateFirst(query, update, UserPracticeRecord.class);
    }
}
