package com.sj.pte.modules.store.bean;/**
 * Created by TUTEHUB on 2021-07-09 14:56.
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
 * @descrption 用户checkout后的订单历史
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("MNOrderHistory")
public class MNOrderHistory extends MNPost {
    private String orderId;
    private String userId;
    private List<String> productId = new ArrayList<>();

}
