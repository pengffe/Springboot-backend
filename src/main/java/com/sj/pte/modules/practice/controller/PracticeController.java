package com.sj.pte.modules.practice.controller;

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
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.sj.pte.modules.practice.bean.MNPractice;
import com.sj.pte.modules.practice.service.PracticeServiceImpl;
import com.sj.pte.modules.question.service.QuestionServiceImpl;
import com.sj.pte.utils.upload.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author pengffe  Email: pengffe@gmail.com
 * @descrption save user's practice to db for each user
 * @date 27-05-2021
 */

@RestController
@RequestMapping("/practice")
public class PracticeController {

    @Autowired
    PracticeServiceImpl practiceServiceImpl;

    @Autowired
    QuestionServiceImpl questionServiceImpl;

    @Autowired
    UploadFileService uploadFileService;

    @PostMapping(value = "/{userId}/{questionId}")
    public ResponseEntity<JSONObject> saveAudioPractice(@PathVariable String userId, @PathVariable String questionId, @RequestParam(value = "file") MultipartFile file) {
        return uploadFileService.saveFileToDisk(questionId, file, userId);
    }

    @PostMapping(value = "")
    public ResponseEntity<JSONObject> saveTextPractice(@RequestBody MNPractice practiceRecord) {
        return practiceServiceImpl.savePracticeToDB(practiceRecord);
    }

    @DeleteMapping("/{id}")
    public DeleteResult deletePracticeById(@PathVariable Long id){
        return practiceServiceImpl.deletePracticeById(id);
    }

    @PutMapping("/{id}/{answer}")
    public UpdateResult updateTextPracticeById(@PathVariable Long id, @PathVariable String answer){
        return practiceServiceImpl.updatePracticeById(id, answer);
    }

    @GetMapping("/{userId}/{questionId}")
    public List<MNPractice> findPracticeByQuestionIdForUser(@PathVariable String userId, @PathVariable String questionId){
        return practiceServiceImpl.findPracticeByQuestionIdForUser(userId, questionId);
    }

    @GetMapping("/{questionId}")
    public List findPracticeByQuestionId(@PathVariable String questionId){
        return practiceServiceImpl.findPracticeByQuestionId(questionId);
    }



//    @GetMapping("/{questionId}")
//    public UserPractice findAllPracticeByUserId(@PathVariable String questionId){
//        return practiceDao.findById(UserPractice.class, userId);
//    }


//    @PostMapping("")
//    public ResponseEntity<JSONObject> savePractice(@RequestBody PracticeRecord practiceRecord, @RequestParam(value = "audioFile", required = false) MultipartFile file){
//        if (null != file){
//            System.out.println("save audio practice");
//            return saveFileService.savePracticeToDisk(practiceRecord.getUserId(), practiceRecord.getQuestionId(), file);
//        }
//        else {
//            System.out.println("save text practice" + practiceRecord.toString());
//        }
//        return null;
//    }

//    @PostMapping(value = "/{userId}/{questionId}")
//    public void saveTextPractice(@PathVariable String userId, @PathVariable String questionId){
//    }

//    @PostMapping(value = "/{userId}/{questionId}")
//    public void findPracticeById(@PathVariable String userId, @PathVariable String questionId){
//
//    }

//    @PostMapping("")
//    public void saveUserPractice(@RequestBody UserPractice userPractice){
//        practiceDao.save(userPractice);
//    }
}
