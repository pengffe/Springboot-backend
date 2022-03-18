package com.pengffe.pte.modules.authServer.dao;

import com.mongodb.client.result.UpdateResult;
import com.pengffe.pte.modules.authServer.entity.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

/**
 * @author pengffe  Email: pengffe@gmail.com
 * @descrption handle data of user
 * @date 27-05-2021
 */

@Component
public class UserDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public boolean save(JwtUser user) {
        if (null != user){
                mongoTemplate.save(user);
                //创建用户时直接自动生成用户练习表
//                VocabularyBook vocabularyBook = new VocabularyBook();
//                vocabularyBook.setUserId(user.getId());
//                mongoTemplate.save(vocabularyBook);
                return true;
        }
        else {
            System.out.println("no instance to save");
        }
        return false;
    }

    public JwtUser findByUsername(String username){
        Query query = new Query(Criteria.where("username").is(username));
        return mongoTemplate.findOne(query, JwtUser.class);
    }

    public JwtUser findByEmail(String email){
        Query query = new Query(Criteria.where("email").is(email));
        return mongoTemplate.findOne(query, JwtUser.class);
    }

    public JwtUser findByPhone(String phone){
        Query query = new Query(Criteria.where("phone").is(phone));
        return mongoTemplate.findOne(query, JwtUser.class);
    }

    public UpdateResult resetPassword(String username, String password){
        Query query = new Query(Criteria.where("username").is(username));
        Update update = new Update().set("password", password);

        return mongoTemplate.updateFirst(query, update, JwtUser.class);
    }

    public boolean enableUser(String email){
        Query query = new Query(Criteria.where("email").is(email));
        Update update = new Update().set("enabled", true);

        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, JwtUser.class);
        if (updateResult.getModifiedCount() == 1 && updateResult.getMatchedCount() == 1){
            return true;
        }
        else {
            return false;
        }
    }
}
