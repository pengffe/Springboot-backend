package com.sj.pte.modules.practice.bean;

import com.sj.pte.general.MNPost;
import com.sj.pte.utils.autoIncKey.AutoIncKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * Created by TUTEHUB on 2021/6/3 12:30 AM.
 * Copyright © 2021 TUTEHUB. All rights reserved.
 * ------------------------
 * Non-disclosure Terms
 * -------------------------
 * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
 * Developer dph agrees with above terms.
 * Technique Support: jobyme88.com
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "MNPractice")
public class MNPractice extends MNPost {

    private String questionId;
    private String answer;//address or text

    private String userId;

//    public MNPractice(String questionId, String answer, String username) {
//        this.questionId = questionId;
//        this.answer = answer;
//        this.username = username;
//    }

}
