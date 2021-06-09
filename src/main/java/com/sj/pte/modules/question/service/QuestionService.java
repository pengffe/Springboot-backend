package com.sj.pte.modules.question.service;/**
 * Created by TUTEHUB on 2021/6/7 4:21 PM.
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
import com.sj.pte.modules.question.bean.MNQuestion;

import java.util.List;

/**
 * @description
 */
public interface QuestionService {


    /***********
     * Create
     ***********/
    <T extends MNQuestion> T save(T t);



    /***********
     * Read
     ***********/
    <T> T findById(Class<T> tCLass, String questionId);

    <T> List<T> findAll(Class<T> tClass);




    <T> DeleteResult deleteById(Class<T> tCLass, String id);
}
