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


import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.result.DeleteResult;
import com.sj.pte.modules.question.bean.MNQuestion;
import com.sj.pte.modules.question.bean.MNQuestionRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @description
 */
public interface QuestionService {

    /***********
     * Create
     ***********/
    <T extends MNQuestion> T save(T t);

    MNQuestion readRequestJSONToObject(String type, MNQuestionRequest mnQuestionRequest);

    /***********
     * Read
     ***********/
    <T> T findById(Class<T> tCLass, String questionId);

    <T> List<T> findAll(Class<T> tClass);

    <T> Page<T> findAllByPage(Class<T> tClass, int pageNum, int pageSize, String sortType);

    <T> Long findCount(Class<T> tClass);

    /***********
     * Update
     ***********/

    <T> DeleteResult deleteById(Class<T> tCLass, String id);
}
