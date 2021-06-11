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
import com.mongodb.client.result.UpdateResult;
import com.sj.pte.modules.practice.bean.MNPractice;
import com.sj.pte.modules.practice.dao.PracticeDao;
import com.sj.pte.modules.question.bean.*;
import com.sj.pte.modules.question.dao.MNQuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


/**
 * @description
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    @Value("${question.filePath}")
    private String filePath;

    private MNQuestionDao mnQuestionDao;

    private CheckServiceImpl checkService;

    @Autowired
    public void setMnQuestionDao(MNQuestionDao mnQuestionDao) {
        this.mnQuestionDao = mnQuestionDao;
    }

    @Autowired
    public void setCheckService(CheckServiceImpl checkService) {
        this.checkService = checkService;
    }

    /***********
     * Create
     ***********/
    @Override
    public <T extends MNQuestion> T save(T t) {
        if (null == t){
            System.out.println("Fail to find the file!");
            return null;
        }

        if (null != mnQuestionDao.findById(t.getClass(), t.getQuestionId())){
            System.out.println("Instance exits");
            return null;
        }

        else {
            return mnQuestionDao.save(t);
        }
    }

    /***********
     * Read
     ***********/
    @Override
    public <T> T findById(Class<T> tCLass, String questionId){
        return mnQuestionDao.findById(tCLass, questionId);
    }

    @Override
    public <T> List<T> findAll(Class<T> tClass) {
        List list = mnQuestionDao.findAll(tClass);
        return checkService.checkDuration(list);
    }

    /***********
     * Delete
     ***********/
    @Override
    public <T> DeleteResult deleteById(Class<T> tCLass, String id) {
        return mnQuestionDao.deleteById(tCLass, id);
    }
}
