package com.sj.pte;/**
 * Created by TUTEHUB on 2021/6/4 1:17 PM.
 * Copyright © 2021 TUTEHUB. All rights reserved.
 * ------------------------
 * Non-disclosure Terms
 * -------------------------
 * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
 * Developer dph agrees with above terms.
 * Technique Support: jobyme88.com
 * Contact: noreply@fengcaoculture.com
 */


import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @description
 */
public interface TutorialRepository extends MongoRepository<Tutorial, String> {
    List<Tutorial> findByTitle(String title);
    List<Tutorial> findByPublished(boolean published);
}