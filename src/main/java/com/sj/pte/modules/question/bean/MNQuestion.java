package com.sj.pte.modules.question.bean;

import com.sj.pte.general.MNPost;
import com.sj.pte.modules.question.QuestionLevelEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * Created by TUTEHUB on 2021/6/3 9:11 AM.
 * Copyright © 2021 TUTEHUB. All rights reserved.
 * ------------------------
 * Non-disclosure Terms
 * -------------------------
 * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
 * Developer dph agrees with above terms.
 * Technique Support: jobyme88.com
 */


/**
 * Lombok will automatically generate isPredicted()
 * Thus, for boolean attributes, no need to include "is" in names
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MNQuestion extends MNPost {

    /**
     * @description different from postID which is automatically generated
     * @values ra-1, rs-1
     */
    private String questionId;

    /***********************
     * Question Information
     **********************/
    private boolean memory;
    /**
     * @description Whether is as same as real test
     */
    private boolean verified;
    private boolean predicted;
    /**
     * @description Automatically adjusted according to postDate and postModified
     */
    private boolean updated;
    /**
     * new question
     * @values default is true, after 7 days, it will become false
     */
    private boolean add = true;
    /**
     * @description how frequent the question appears,
     * @frontend would indicate with stars
     * @values 0 not frequent
     */
    private int frequency;

    /**
     * @description Teacher tag
     */
    private QuestionLevelEnum level;
    private List<Date> testedDates;
    /**
     * @description Where the question is from
     */
    private String source;


    /***********************
     * Analysis Information
     **********************/
    private String lessonPath;
}
