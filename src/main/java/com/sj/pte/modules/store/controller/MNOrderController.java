package com.sj.pte.modules.store.controller;/**
 * Created by TUTEHUB on 2021-07-08 15:13.
 * Copyright © 2021 TUTEHUB. All rights reserved.
 * ------------------------
 * Non-disclosure Terms
 * -------------------------
 * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
 * Developer fengpeng agrees with above terms.
 * Technique Support: jobyme88.com
 */

import com.alibaba.fastjson.JSONObject;
import com.sj.pte.modules.store.request.model.CheckoutRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @descrption
 */

@RestController
@RequestMapping("/order")
public class MNOrderController {

    @PostMapping("/{userId}/{productId}")
    public ResponseEntity<JSONObject> addOrder(@PathVariable String userId, @PathVariable String productId){
        return null;
    }


    @GetMapping("/{userId}/{productId}")
    public ResponseEntity<JSONObject> showOrder(@PathVariable String userId, @PathVariable String productId){
        return null;
    }

    // 更新订单状态，是否已购买
    @PutMapping
    public ResponseEntity<JSONObject> checkoutOrder(@RequestBody CheckoutRequest checkoutRequest){
        return null;
    }
}
