package com.sj.pte.modules.jobApplication;/**
 * Created by TUTEHUB on 2021-06-28 15:49.
 * Copyright © 2021 TUTEHUB. All rights reserved.
 * ------------------------
 * Non-disclosure Terms
 * -------------------------
 * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
 * Developer fengpeng agrees with above terms.
 * Technique Support: jobyme88.com
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

/**
 * @descrption
 */
@Component
public class MNApplicantDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public MNApplicant save(MNApplicant applicant) {
        if (null != applicant){
            return mongoTemplate.save(applicant);
        }
        else {
            System.out.println("no instance to save");
            return null;
        }
    }
}
