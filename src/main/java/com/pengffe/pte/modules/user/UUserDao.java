package com.pengffe.pte.modules.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class UUserDao {
    @Autowired
    private MongoTemplate mongoTemplate;



}
