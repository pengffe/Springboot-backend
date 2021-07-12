package com.sj.pte.modules.store.bean;/**
 * Created by TUTEHUB on 2021-07-08 14:51.
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
 * @descrption used as store information of lessons bought by users
 *              用户拍下的产品，但并未付款，保存在购物车中
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("MNTrolley")
public class MNTrolley extends MNPost {
    private String userId;
    private List<String> productId = new ArrayList<>();
}
