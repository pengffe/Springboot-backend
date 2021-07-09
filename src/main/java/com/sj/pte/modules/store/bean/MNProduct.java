package com.sj.pte.modules.store.bean;/**
 * Created by TUTEHUB on 2021-07-08 14:57.
 * Copyright © 2021 TUTEHUB. All rights reserved.
 * ------------------------
 * Non-disclosure Terms
 * -------------------------
 * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
 * Developer fengpeng agrees with above terms.
 * Technique Support: jobyme88.com
 */

import com.sj.pte.general.MNPost;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * @descrption
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("MNProduct")
public class MNProduct extends MNPost {
    private String productId;
    private String productName;
    private String price;
    private List<String> productPath;
}
