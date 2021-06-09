package com.sj.pte.general;/**
 * Created by TUTEHUB on 2021/6/4 7:04 AM.
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description
 */
@Component
public class MNPostDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<MNPost> findAll() {
        return mongoTemplate.findAll(MNPost.class);
    }

    public List<MNPost> findByTitle(String title) {
        Query query = new Query(Criteria.where("title").is(title));
        return mongoTemplate.findAll(MNPost.class);
    }

    public List<MNPost> findByPublished(boolean isPublished) {
        Query query = new Query(Criteria.where("postStatus").is(isPublished ? "published" : "draft"));
        return mongoTemplate.findAll(MNPost.class);
    }

    public MNPost findById(Long id) {
        Query query = new Query(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, MNPost.class);
    }

    public MNPost save(MNPost post) {
        if (null != post){
            return mongoTemplate.save(post);
        }
        else {
            System.out.println("no instance to save");
            return null;
        }
    }

    public MNPost updateById(Long id, MNPost post) {
        Query query = new Query(Criteria.where("id").is(id));

        MNPost _post = mongoTemplate.findOne(
                Query.query(Criteria.where("id").is("id")), MNPost.class);
        _post.setAuthor(post.getAuthor());
        _post.setPostDate(post.getPostDate());
        _post.setContent(post.getContent());
        _post.setStatus(post.getStatus());
        _post.setTitle(post.getTitle());
//        _post.setPostModified(post.getPostModified());
        return mongoTemplate.save(_post);
    }

    public DeleteResult deleteById(Long id) {
        Query query = new Query(Criteria.where("id").is(id));
        return mongoTemplate.remove(query, MNPost.class);
    }

}
