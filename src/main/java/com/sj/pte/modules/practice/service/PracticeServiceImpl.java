package com.sj.pte.modules.practice.service;

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
import com.sj.pte.modules.practice.bean.MNUserCollectRecord;
import com.sj.pte.modules.practice.bean.MNUserPracticeRecord;
import com.sj.pte.modules.practice.dao.PracticeDao;
import com.sj.pte.modules.practice.dao.UserCollectRecordDao;
import com.sj.pte.modules.practice.dao.UserPracticeRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author pengffe  Email: pengffe@gmail.com
 * @descrption save audio/image file to local disk
 * @date 28-05-2021
 */

@Service
public class PracticeServiceImpl implements PracticeService {

    @Autowired
    PracticeDao practiceDao;

    @Autowired
    UserPracticeRecordDao userPracticeRecordDao;

    @Autowired
    UserCollectRecordDao userCollectRecordDao;

    @Override
    public UpdateResult saveCollectQuestionId(String userId, String questionId) {
        return userCollectRecordDao.updateById(userId,questionId);
    }

    @Override
    public MNUserCollectRecord saveCollectRecord(String userId) {
        MNUserCollectRecord mnUserCollectRecord = new MNUserCollectRecord();
        mnUserCollectRecord.setUserId(userId);
        return userCollectRecordDao.save(mnUserCollectRecord);
    }

    @Override
    public UpdateResult removeCollectQuestionId(String userId, String questionId) {
        return userCollectRecordDao.deleteById(userId, questionId);
    }

    @Override
    public UpdateResult savePracticeQuestionId(String userId, String questionId) {
        return userPracticeRecordDao.updateById(userId, questionId);
    }

    @Override
    public MNUserPracticeRecord savePracticeRecord(String userId) {
        MNUserPracticeRecord mnUserPracticeRecord = new MNUserPracticeRecord();
        mnUserPracticeRecord.setUserId(userId);
        return userPracticeRecordDao.save(mnUserPracticeRecord);
    }

    @Override
    public UpdateResult resetPracticeRecord(String userId) {
        return userPracticeRecordDao.resetByUserId(userId);
    }

    @Override
    public ResponseEntity<JSONObject> savePracticeToDB(MNPractice practiceRecord) {
        JSONObject json = new JSONObject();
        if (null != practiceDao.save(practiceRecord)){
            json.put("STATUS", "200");
            json.put("MSG", "上传答案成功");
            return new ResponseEntity<>(json, HttpStatus.OK);
        }
        else{
            json.put("STATUS", "ERROR");
            json.put("MSG", "上传答案失败");
            return new ResponseEntity<>(json, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<MNPractice> findPracticeByQuestionIdForUser(String userId, String questionId) {
        return practiceDao.findByQuestionIdForUser(userId, questionId);
    }

    @Override
    public List findPracticeByQuestionId(String questionId) {
        return practiceDao.findByQuestionId(questionId);
    }

    @Override
    public DeleteResult deletePracticeById(Long id){
        return practiceDao.deleteById(id);
    }

    @Override
    public UpdateResult updatePracticeById(Long id, String answer) {
        return practiceDao.updateById(id, answer);
    }
}
