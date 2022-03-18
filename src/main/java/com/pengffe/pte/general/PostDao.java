package com.pengffe.pte.general;


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
public class PostDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Post> findAll() {
        return mongoTemplate.findAll(Post.class);
    }

    public List<Post> findByTitle(String title) {
        Query query = new Query(Criteria.where("title").is(title));
        return mongoTemplate.findAll(Post.class);
    }

    public List<Post> findByPublished(boolean isPublished) {
        Query query = new Query(Criteria.where("postStatus").is(isPublished ? "published" : "draft"));
        return mongoTemplate.findAll(Post.class);
    }

    public Post findById(Long id) {
        Query query = new Query(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, Post.class);
    }

    public Post save(Post post) {
        if (null != post){
            return mongoTemplate.save(post);
        }
        else {
            System.out.println("no instance to save");
            return null;
        }
    }

    public Post updateById(Long id, Post post) {
        Query query = new Query(Criteria.where("id").is(id));

        Post _post = mongoTemplate.findOne(
                Query.query(Criteria.where("id").is("id")), Post.class);
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
        return mongoTemplate.remove(query, Post.class);
    }

}
