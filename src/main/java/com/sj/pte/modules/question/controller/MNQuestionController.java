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
import com.sj.pte.modules.question.bean.MNASQ;
import com.sj.pte.modules.question.bean.MNQuestion;
import com.sj.pte.modules.question.bean.MNQuestionRequest;
import com.sj.pte.modules.question.service.CheckService;
import com.sj.pte.modules.question.service.QuestionServiceImpl;
import com.sj.pte.utils.json.JSONUtil;
import com.sj.pte.utils.upload.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
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

    private UploadFileService uploadFileService;

    private CheckService checkService;

    @Autowired
    public void setCheckService(CheckService checkService) {
        this.checkService = checkService;
    }

    @Autowired
    public void setUploadFileService(UploadFileService uploadFileService) {
        this.uploadFileService = uploadFileService;
    }

    @Autowired
    public void setQuestionService(QuestionServiceImpl questionService) {
        this.questionService = questionService;
    }

    /***********
     * Create
     ***********/
    @PostMapping(value = "/local/{type}/{id}")
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

    @PostMapping(value = "/local/{type}")
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

    @PostMapping(value = "/{type}")
    public ResponseEntity<String> addOneByPostman(@RequestBody MNQuestionRequest mnQuestionRequest, @PathVariable String type){
        MNQuestion mnQuestion = questionService.readRequestJSONToObject(type, mnQuestionRequest);
        if (null == mnQuestion){
            return new ResponseEntity<>("URL TYPE IS NOT SAME AS JSON TYPE", HttpStatus.CONFLICT);
        }
        if (null != questionService.save(mnQuestion)){
            System.out.println(mnQuestionRequest.toString());
            return new ResponseEntity<>("SUCCESS TO ADD", HttpStatus.OK);
        }
        else return new ResponseEntity<>("INSTANCE EXITS", HttpStatus.CREATED);
    }

    /***********
     * Read
     ***********/
    @GetMapping(value = "/{type}/{id}")
    public MNQuestion findById(@PathVariable String type, @PathVariable String id) {
        String questionId = type + "-" + id;
        return questionService.findById(checkService.checkType(type).getClass(), questionId);
    }

//    @GetMapping(value = "/{type}")
//    public List findAll(@PathVariable String type) {
//        return questionService.findAll(checkService.checkType(type).getClass());
//    }

    @GetMapping(value = "/{type}")
    public Page findAllByPage(@PathVariable String type,
                              @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                              @RequestParam(value = "pageSize", defaultValue = "0")  int pageSize,
                              @RequestParam(value = "sortType", defaultValue = "ASC")  String sortType) {
        return questionService.findAllByPage(checkService.checkType(type).getClass(), pageNum, pageSize, sortType);
    }

    @GetMapping(value = "/count/{type}")
    public Long findCountByType(@PathVariable String type){
        return questionService.findCount(checkService.checkType(type).getClass());
    }

    /***********
     * Update
     ***********/
    @PutMapping(value = "/{type}/{id}")
    public ResponseEntity<JSONObject> updateFileById(@PathVariable String type,
                                                     @PathVariable String id,
                                                     @RequestParam(value = "file", required = false) MultipartFile file){
        String questionId = type + "-" + id;
        return uploadFileService.saveFileToDisk(questionId, file, null);
    }

    @PatchMapping(value = "/{type}/{id}")
    public void updateAttribute(@RequestBody MNQuestionRequest mnQuestionRequest,
                                @PathVariable String type,
                                @PathVariable String id){
        questionService.updateById(checkService.checkType(type).getClass(), id, mnQuestionRequest);
    }

    /***********
     * Delete
     ***********/
    @DeleteMapping("/{type}/{id}")
    public void deleteById(@PathVariable String type, @PathVariable String id){
        String questionId = type + "-" + id;
        questionService.deleteById(checkService.checkType(type).getClass(), questionId);
    }
}