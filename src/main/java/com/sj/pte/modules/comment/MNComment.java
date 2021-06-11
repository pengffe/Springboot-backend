package com.sj.pte.modules.comment;

import com.sj.pte.utils.autoIncKey.AutoIncKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("MNComment")
public class MNComment {
    @Id
    @AutoIncKey
    private Long id;
    String questionId;
    String userId;
    String message;
}
