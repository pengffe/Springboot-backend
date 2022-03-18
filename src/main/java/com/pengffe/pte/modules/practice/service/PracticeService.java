package com.pengffe.pte.modules.practice.service;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.pengffe.pte.modules.practice.bean.Practice;
import com.pengffe.pte.modules.practice.bean.UserCollectRecord;
import com.pengffe.pte.modules.practice.bean.UserPracticeRecord;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PracticeService {

//    /**
//     * 将音频练习文件存入服务器，并将文件路径存入数据库
//     */
//    ResponseEntity<JSONObject> savePracticeToDisk(String userId, String questionId, MultipartFile file);

    /**
     * 将收藏过的ID加入收藏记录表中，表示此题已收藏
     */
    UpdateResult saveCollectQuestionId(String userId, String questionId);

    /**
     * 创建用户收藏记录表，记录哪些题被收藏
     */
    UserCollectRecord saveCollectRecord(String userId);

    /**
     * 取消收藏
     */
    UpdateResult removeCollectQuestionId(String userId, String questionId);


    /**
     * 将练习过的ID加入练习记录表中，表示此题已练习
     */
    UpdateResult savePracticeQuestionId(String userId, String questionId);

    /**
     * 创建用户练习记录表，记录那些题已被练习过
     */
    UserPracticeRecord savePracticeRecord(String userId);

    /**
     * 重置练习记录表，清空所有记录数据
     */
    UpdateResult resetPracticeRecord(String userId);

    /**
     * 将文本练习内容直接存入数据库
     */
    ResponseEntity<JSONObject>  savePracticeToDB(Practice practiceRecord);

    /**
     * 根据用户id， 查找ta某一道题的所有练习
     */
    List<Practice> findPracticeByQuestionIdForUser(String userId, String questionId);

    /**
     * 找到某一个题的所有练习
     */
    List findPracticeByQuestionId(String questionId);

    /**
     * 查找某一题的所有练习
     */
    DeleteResult deletePracticeById(Long id);

    /**
     * 修改文本练习的内容
     */
    UpdateResult updatePracticeById(Long id, String answer);
}
