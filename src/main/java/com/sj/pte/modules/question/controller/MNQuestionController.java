package com.sj.pte.modules.question.controller;

/**
 * Created by TUTEHUB on 2021-05-31 16:46.
 * Copyright © 2021 TUTEHUB. All rights reserved.
 * ------------------------
 * Non-disclosure Terms
 * -------------------------
 * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
 * Developer fengpeng agrees with above terms.
 * Technique Support: jobyme88.com
 */

import com.alibaba.fastjson.JSONObject;
import com.sj.pte.modules.question.bean.MNQuestion;
import com.sj.pte.modules.question.service.QuestionServiceImpl;
import com.sj.pte.utils.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author pengffe  Email: pengffe@gmail.com
 * @descrption base class for question request
 * @date 26-05-2021
 */


@RestController
@RequestMapping("/question")
public class MNQuestionController {

    private QuestionServiceImpl questionService;

    @Autowired
    public void setQuestionService(QuestionServiceImpl questionService) {
        this.questionService = questionService;
    }


    /***********
     * Create
     ***********/
    @PostMapping(value = "/{type}/{id}")
    public ResponseEntity<JSONObject> addOne(@PathVariable String type, @PathVariable int id) {

        String questionId = type + "-" + id;
        MNQuestion mnQuestion;

        ResponseEntity<JSONObject> jsonResponse = JSONUtil.readLocalJSONToQuestion(questionId);

        if (jsonResponse.getStatusCode().equals(HttpStatus.OK)){
            mnQuestion =(MNQuestion) jsonResponse.getBody().get("MSG");
            if (null != questionService.save(mnQuestion)){
                return jsonResponse;
            }
            else {
                JSONObject json = new JSONObject();
                json.put("MSG", "Fail to save or file exits. " + mnQuestion.getQuestionId());
                return new ResponseEntity<>(json, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else {
            return jsonResponse;
        }
    }

    @PostMapping(value = "{type}")
    public ResponseEntity<JSONObject> addAll(@PathVariable String type) {
        JSONObject json = new JSONObject();
        int i = 228;
        while (true) {
            ResponseEntity<JSONObject> jsonObjectResponseEntity = addOne(type, i);
            if (!jsonObjectResponseEntity.getStatusCode().equals(HttpStatus.OK)){
                json.put(type + i, jsonObjectResponseEntity.getBody().get("MSG"));
            }
            if (jsonObjectResponseEntity.getStatusCode().equals(HttpStatus.NOT_FOUND)){
                return new ResponseEntity<>(json, HttpStatus.OK);
            }
            i++;
        }
    }


    /***********
     * Read
     ***********/
    @GetMapping(value = "/{type}/{id}")
    public MNQuestion findById(@PathVariable String type, @PathVariable String id) {
        String questionId = type + "-" + id;
        return questionService.findById(questionService.checkType(type).getClass(), questionId);
    }

    @GetMapping(value = "{type}")
    public List findAll(@PathVariable String type) {
        return questionService.findAll(questionService.checkType(type).getClass());
    }


    /***********
     * Update
     ***********/



    /***********
     * Delete
     ***********/
    @DeleteMapping("/{type}/{id}")
    public void deleteById(@PathVariable String type, @PathVariable String id){
        String questionId = type + "-" + id;
        questionService.deleteById(questionService.checkType(type).getClass(), questionId);
    }






}