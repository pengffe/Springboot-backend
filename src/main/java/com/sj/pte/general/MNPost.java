package com.sj.pte.general;

/**
 * Created by TUTEHUB on 2021/6/1 11:52 AM.
 * Copyright © 2021 TUTEHUB. All rights reserved.
 * ------------------------
 * Non-disclosure Terms
 * -------------------------
 * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
 * Developer dph agrees with above terms.
 * Technique Support: jobyme88.com
 */


import com.sj.pte.utils.autoIncKey.AutoIncKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @description The most general bean
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("post")
public class MNPost {
    @Id
    @AutoIncKey
    private Long id;

    private String title;
    private String content;
    /**
     * @description When the post is published
     */
    private Date postDate = new Date();
    /**
     * @description When the post is lately modified
     */
    private Date modifiedDate;
    /**
     *
     */
    private String author;
    /**
     * @values Draft / Published / Open to Employees
     */
    private String status;





}
