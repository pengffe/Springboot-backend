package com.sj.pte.modules.store.response.model;/**
 * Created by TUTEHUB on 2021-07-14 12:35.
 * Copyright © 2021 TUTEHUB. All rights reserved.
 * ------------------------
 * Non-disclosure Terms
 * -------------------------
 * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
 * Developer fengpeng agrees with above terms.
 * Technique Support: jobyme88.com
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @descrption
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderHistoryResponse {
    private String orderId;
    private Map<String, String> productInfo = new HashMap<>();
}
