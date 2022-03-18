package com.pengffe.pte.modules.jobApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

/**
 * @descrption
 */
@Component
public class ApplicantDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public Applicant save(Applicant applicant) {
        if (null != applicant){
            return mongoTemplate.save(applicant);
        }
        else {
            System.out.println("no instance to save");
            return null;
        }
    }
}
