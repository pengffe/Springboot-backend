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
import com.mongodb.client.result.UpdateResult;
import com.sj.pte.modules.question.bean.*;
import com.sj.pte.modules.question.dao.MNQuestionDao;
import lombok.Builder;
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
            System.out.println("Fail to find local file or request json is null!");
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

    /**
     * 根据postman传来的json对象，生成相应题目的实例，用于存入db
     * @param type
     * @param mnQuestionRequest
     * @return
     */
    @Override
    public MNQuestion readRequestJSONToObject(String type, MNQuestionRequest mnQuestionRequest){

        // unique
        MNQuestion mnQuestion = new MNQuestion();
        String audioPath;
        String imagePath;
        String question;
        List<String> options;
        List<String> sampleAnswers;
        if (!type.equals(mnQuestionRequest.getQuestionId().split("-")[0])){
            return null;
        }
        switch (type) {
            /**
             * speaking
             */
            case ("ra"):
                mnQuestion = new MNRA();
                break;
            case ("rs"):
                audioPath = mnQuestionRequest.getAudioPath();
                mnQuestion = new MNRS(audioPath);
                break;
            case ("rl"):
                audioPath = mnQuestionRequest.getAudioPath();
                imagePath = mnQuestionRequest.getImagePath();
                mnQuestion = new MNRL(audioPath, imagePath);
                break;
            case ("di"):
                imagePath = mnQuestionRequest.getImagePath();
                sampleAnswers = mnQuestionRequest.getSampleAnswers();
                mnQuestion = new MNDI(imagePath, sampleAnswers);
                break;
            case ("asq"):
                audioPath = mnQuestionRequest.getAudioPath();
                mnQuestion = new MNASQ(audioPath);
                break;

            /**
             * reading
             */
            case ("rmcs"):
                question = mnQuestionRequest.getQuestion();
                options = mnQuestionRequest.getOptions();
                mnQuestion = new MNRMCS(question, options);
                break;
            case("rmcm"):
                question = mnQuestionRequest.getQuestion();
                options = mnQuestionRequest.getOptions();
                mnQuestion = new MNRMCM(question, options);
                break;
            case("ro"):
                mnQuestion = new MNRO();
                break;
            case("rfib"):
                options = mnQuestionRequest.getOptions();
                mnQuestion = new MNRFIB(options);
                break;
            case("rwfib"):
                mnQuestion = new MNRWFIB();
                break;

            /**
             * listening
             */
            case("fib"):
                audioPath = mnQuestionRequest.getAudioPath();
                mnQuestion = new MNFIB(audioPath);
                break;
            case("hcs"):
                audioPath = mnQuestionRequest.getAudioPath();
                options = mnQuestionRequest.getOptions();
                mnQuestion = new MNHCS(audioPath, options);
                break;
            case("hiw"):
                audioPath = mnQuestionRequest.getAudioPath();
                mnQuestion = new MNHIW(audioPath);
                break;
            case("wfd"):
                audioPath = mnQuestionRequest.getAudioPath();
                mnQuestion = new MNWFD(audioPath);
                break;
            case("mcm"):
                question = mnQuestionRequest.getQuestion();
                audioPath = mnQuestionRequest.getAudioPath();
                options = mnQuestionRequest.getOptions();
                mnQuestion = new MNMCM(question, audioPath, options);
                break;
            case("mcs"):
                question = mnQuestionRequest.getQuestion();
                audioPath = mnQuestionRequest.getAudioPath();
                options = mnQuestionRequest.getOptions();
                mnQuestion = new MNMCS(question, audioPath, options);
                break;
            case("smw"):
                audioPath = mnQuestionRequest.getAudioPath();
                options = mnQuestionRequest.getOptions();
                mnQuestion = new MNSMW(audioPath, options);
                break;
            case("sst"):
                audioPath = mnQuestionRequest.getAudioPath();
                mnQuestion = new MNSST(audioPath);
                break;

            /**
             * writing
             */
            case("swt"):
                mnQuestion = new MNSWT();
                break;
            case("we"):
                mnQuestion = new MNWE();
                break;
        }

        // common
        mnQuestion.setQuestionId(mnQuestionRequest.getQuestionId());
        mnQuestion.setTitle(mnQuestionRequest.getTitle());
        mnQuestion.setContent(mnQuestionRequest.getContent());
        mnQuestion.setLessonPath(mnQuestionRequest.getLessonPath());

        return mnQuestion;
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


    @Override
    public <T> UpdateResult updateById(Class<T> tClass, String id, MNQuestionRequest mnQuestionRequest){
        UpdateResult updateResult = null;
        //whether the question content, such as text, audio, image, question, options, answer, is upadated
        boolean isInfoUpdate = false;

        if(null != mnQuestionRequest.getTitle()){
            updateResult = mnQuestionDao.updateTitleById(tClass, id, mnQuestionRequest.getTitle());
        }
        else if(null != mnQuestionRequest.getContent()){
            updateResult = mnQuestionDao.updateContentById(tClass, id, mnQuestionRequest.getContent());
            isInfoUpdate = true;
        }
        else if(mnQuestionRequest.isVerified()){
            updateResult = mnQuestionDao.updateVerifiedById(tClass, id, true);
        }
        else if(mnQuestionRequest.isPredicted()){
            updateResult = mnQuestionDao.updatePredictedById(tClass, id, true);
        }
        else if(0 != mnQuestionRequest.getFrequency()){
            updateResult = mnQuestionDao.updateFrequencyById(tClass, id, mnQuestionRequest.getFrequency());
        }
        else if(null != mnQuestionRequest.getLevel()){
            updateResult = mnQuestionDao.updateLevelById(tClass, id, mnQuestionRequest.getLevel());
        }
        else if(null != mnQuestionRequest.getTestDate()){
            updateResult = mnQuestionDao.updateTestDateById(tClass, id, mnQuestionRequest.getTestDate());
        }
        else if(null != mnQuestionRequest.getSource()){
            updateResult = mnQuestionDao.updateSourceById(tClass, id, mnQuestionRequest.getSource());
        }
        else if(null != mnQuestionRequest.getLessonPath()){
            updateResult = mnQuestionDao.updateLessonById(tClass, id, mnQuestionRequest.getLessonPath());
        }
        else if(null != mnQuestionRequest.getAudioPath()){
            updateResult = mnQuestionDao.updateAudioById(tClass, id, mnQuestionRequest.getAudioPath());
            isInfoUpdate = true;
        }
        else if(null != mnQuestionRequest.getImagePath()){
            updateResult = mnQuestionDao.updateImageById(tClass, id, mnQuestionRequest.getImagePath());
            isInfoUpdate = true;
        }
        else if(null != mnQuestionRequest.getOptions()){
            updateResult = mnQuestionDao.updateOptionsById(tClass, id, mnQuestionRequest.getOptions());
            isInfoUpdate = true;
        }
        else if(null != mnQuestionRequest.getQuestion()){
            updateResult = mnQuestionDao.updateQuestionById(tClass, id, mnQuestionRequest.getQuestion());
            isInfoUpdate = true;
        }

        if (isInfoUpdate){
            mnQuestionDao.updateModifiedDateById(tClass, id);
        }
        return updateResult;
    }

    /***********
     * Delete
     ***********/
    @Override
    public <T> DeleteResult deleteById(Class<T> tCLass, String id) {
        return mnQuestionDao.deleteById(tCLass, id);
    }
}
