package com.sj.pte.modules.practice.controller;/**
 * Created by TUTEHUB on 2021-07-07 14:15.
 * Copyright © 2021 TUTEHUB. All rights reserved.
 * ------------------------
 * Non-disclosure Terms
 * -------------------------
 * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
 * Developer fengpeng agrees with above terms.
 * Technique Support: jobyme88.com
 */

import com.mongodb.client.result.UpdateResult;
import com.sj.pte.modules.practice.service.PracticeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @descrption
 */

@RestController
@RequestMapping("/collect")
public class CollectController {

    @Autowired
    PracticeServiceImpl practiceServiceImpl;

    // 添加收藏
    @PostMapping(value = "/{userId}/{questionId}")
    public ResponseEntity<String> addCollect(@PathVariable String userId, @PathVariable String questionId) {
        UpdateResult updateResult = practiceServiceImpl.saveCollectQuestionId(userId, questionId);
        if (1 == updateResult.getMatchedCount() && 1 == updateResult.getModifiedCount()){
            return new ResponseEntity<>("SUCCESS TO COLLECT", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("FAIL TO COLLECT", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 取消收藏
    @DeleteMapping(value = "/{userId}/{questionId}")
    public ResponseEntity<String> removeCollect(@PathVariable String userId, @PathVariable String questionId){
        UpdateResult updateResult = practiceServiceImpl.removeCollectQuestionId(userId, questionId);
        if (1 == updateResult.getMatchedCount() && 1 == updateResult.getModifiedCount()){
            return new ResponseEntity<>("SUCCESS TO REMOVE COLLECT", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("FAIL TO REMOVE COLLECT", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
