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
import org.springframework.stereotype.Service;

import java.util.List;



/**
 * @description
 */
@Service
public class QuestionServiceImpl implements QuestionService {
    private MNQuestionDao mnQuestionDao;

    private CheckDurationServiceImpl checkDurationService;

    @Autowired
    public void setMnQuestionDao(MNQuestionDao mnQuestionDao) {
        this.mnQuestionDao = mnQuestionDao;
    }
    public MNQuestionDao getMnQuestionDao() {
        return mnQuestionDao;
    }

    @Autowired
    public void setCheckDurationService(CheckDurationServiceImpl checkDurationService) {
        this.checkDurationService = checkDurationService;
    }
    public CheckDurationServiceImpl getCheckDurationService() {
        return checkDurationService;
    }






    public MNQuestion checkType(String questionType) {
        switch (questionType) {
            /**
             * speaking
             */
            case ("ra"):
                return new MNRA();
            case ("rs"):
                return new MNRS();
            case ("rl"):
                return new MNRL();
            case ("di"):
                return new MNDI();
            case ("asq"):
                return new MNASQ();

            /**
             * reading
             */
            case ("rmcs"):
                return new MNRMCS();
            case("rmcm"):
                return new MNRMCM();
            case("ro"):
                return new MNRO();
            case("rfib"):
                return new MNRFIB();
            case("rwfib"):
                return new MNRWFIB();

            /**
             * listening
             */
            case("fib"):
                return new MNFIB();
            case("hcs"):
                return new MNHCS();
            case("hiw"):
                return new MNHIW();
            case("mcm"):
                return new MNMCM();
            case("mcs"):
                return new MNMCS();
            case("smw"):
                return new MNSMW();
            case("sst"):
                return new MNSST();
            case("wfd"):
                return new MNWFD();

            /**
             * writing
             */
            case("swt"):
                return new MNSWT();
            case("we"):
                return new MNWE();
        }
        return new MNQuestion();
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
        return checkDurationService.checkDuration(list);
    }



    /***********
     * Delete
     ***********/
    @Override
    public <T> DeleteResult deleteById(Class<T> tCLass, String id) {
        return mnQuestionDao.deleteById(tCLass, id);
    }
}
