package com.sj.pte.modules.store.dao;/**
 * Created by TUTEHUB on 2021-07-09 15:03.
 * Copyright © 2021 TUTEHUB. All rights reserved.
 * ------------------------
 * Non-disclosure Terms
 * -------------------------
 * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
 * Developer fengpeng agrees with above terms.
 * Technique Support: jobyme88.com
 */

import com.mongodb.client.result.UpdateResult;
import com.sj.pte.modules.store.bean.MNProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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
public class MNOrderDao {

    @Autowired
    MongoTemplate mongoTemplate;

    public <T> T save(T t) {
        if (null != t){
            return mongoTemplate.save(t);
        }
        else {
            System.out.println("no instance to save");
            return null;
        }
    }

    public <T> T findById(Class<T> tCLass, String userId) {
        Query query = new Query(Criteria.where("userId").is(userId));
        return mongoTemplate.findOne(query, tCLass);
    }

    public <T> List<T> findAllById(Class<T> tCLass, String userId) {
        Query query = new Query(Criteria.where("userId").is(userId));
        return mongoTemplate.find(query, tCLass);
    }

    public <T> UpdateResult updateProductIdById(Class<T> tCLass, String userId, List<String> productId, boolean option){
        Query query = new Query(Criteria.where("userId").is(userId));
        Update update = new Update();
        // 添加产品清单
        if (option){
            update.addToSet("productId", productId);
        }
        // 移除产品清单
        else {
            for (String item: productId
                 ) {
                update.pull("productId", item);
            }
        }
        return mongoTemplate.updateFirst(query, update, tCLass);
    }
}
