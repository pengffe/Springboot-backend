package com.sj.pte.modules.store.dao;/**
 * Created by TUTEHUB on 2021-07-08 15:20.
 * Copyright © 2021 TUTEHUB. All rights reserved.
 * ------------------------
 * Non-disclosure Terms
 * -------------------------
 * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
 * Developer fengpeng agrees with above terms.
 * Technique Support: jobyme88.com
 */

import com.mongodb.client.result.UpdateResult;
import com.sj.pte.modules.jobApplication.MNApplicant;
import com.sj.pte.modules.store.bean.MNProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @descrption
 */

@Repository
public class MNProductDao {

    @Autowired
    MongoTemplate mongoTemplate;

    public MNProduct save(MNProduct product) {
        if (null != product){
            return mongoTemplate.save(product);
        }
        else {
            System.out.println("no instance to save");
            return null;
        }
    }

    public <T> T findById(Class<T> tCLass, String id) {
        Query query = new Query(Criteria.where("productId").is(id));
        return mongoTemplate.findOne(query, tCLass);
    }

    public <T> List<T> findAll(Class<T> tClass) {
        return mongoTemplate.findAll(tClass);
    }

    public UpdateResult updateVideoLessonById(MNProduct mnProduct){
        Query query = new Query(Criteria.where("productId").is(mnProduct.getProductId()));
        Update update = new Update().set("videoLesson", mnProduct.getVideoLesson());

        return mongoTemplate.upsert(query, update, MNProduct.class);
    }
}
