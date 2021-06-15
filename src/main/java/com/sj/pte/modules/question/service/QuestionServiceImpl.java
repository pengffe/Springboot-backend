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
import com.sj.pte.modules.question.bean.*;
import com.sj.pte.modules.question.dao.MNQuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public <T> Page<T> findAllByPage(Class<T> tClass, int pageNum, int pageSize, String sortType){
        return mnQuestionDao.findAllByPage(tClass, pageNum - 1, pageSize, sortType);
    }

    @Override
    public <T> Long findCount(Class<T> tClass){
        return mnQuestionDao.findCount(tClass);
    }

    /***********
     * Delete
     ***********/
    @Override
    public <T> DeleteResult deleteById(Class<T> tCLass, String id) {
        return mnQuestionDao.deleteById(tCLass, id);
    }
}
