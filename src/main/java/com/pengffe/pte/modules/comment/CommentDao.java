package com.pengffe.pte.modules.comment;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author pengffe  Email: pengffe@gmail.com
 * @descrption handle comments for each question
 * @date 26-05-2021
 */

@Component
public class CommentDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public Comment addComment(Comment comment){
        return mongoTemplate.save(comment);
    }

    public List<Comment> findAllByQuestionId(String id){
        Query query = new Query(Criteria.where("questionId").is(id));
        return mongoTemplate.find(query, Comment.class);
    }

    public DeleteResult deleteById(Long id){
        Query query = new Query(Criteria.where("id").is(id));
        return mongoTemplate.remove(query, Comment.class);
    }

    public UpdateResult updateMessage(String id, String message){
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update().set("message", message);

        return mongoTemplate.updateFirst(query, update, Comment.class);
    }
}
