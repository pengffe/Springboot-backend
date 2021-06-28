package com.sj.pte.modules.jobApplication;/**
 * Created by TUTEHUB on 2021-06-28 15:07.
 * Copyright © 2021 TUTEHUB. All rights reserved.
 * ------------------------
 * Non-disclosure Terms
 * -------------------------
 * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
 * Developer fengpeng agrees with above terms.
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
 * @descrption
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("MNApplicant")
public class MNApplicant {
    @Id
    @AutoIncKey
    private long id;

    private String firstName;
    private String lastName;

    private String email;
    private String phone;

    private Date birth;
    private String visa;
    private String CVPath;
}
