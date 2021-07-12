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

import com.sj.pte.modules.store.bean.MNTrolley;
import com.sj.pte.modules.store.bean.MNOrderHistory;
import com.sj.pte.modules.store.request.model.CheckoutRequest;
import com.sj.pte.modules.store.service.MNOrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @descrption
 */

@RestController
@RequestMapping("/order")
public class MNOrderController {

    @Autowired
    MNOrderServiceImpl mnOrderService;

    @PostMapping("")
    public ResponseEntity<String> addTrolley(@RequestBody MNTrolley mnTrolley){
        MNTrolley mnTrolleyResult = mnOrderService.addProductsToTrolley(mnTrolley.getUserId(), mnTrolley.getProductId());
        if (null != mnTrolleyResult){
            return new ResponseEntity<>("SUCCESS TO ADD", HttpStatus.OK);
        }
        else return new ResponseEntity<>("FAIL TO ADD", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @GetMapping("/{userId}")
    public ResponseEntity<List> showTrolley(@PathVariable String userId){
        List<String> mnProductIds = mnOrderService.showProductsInTrolley(userId);
        if (null != mnProductIds){
            System.out.println("null");
            return new ResponseEntity<>(mnProductIds, HttpStatus.OK);
        }
        else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    // 更新订单状态，是否已购买
    @PutMapping("")
    public ResponseEntity<MNOrderHistory> checkoutOrder(@RequestBody CheckoutRequest checkoutRequest){
        MNOrderHistory checkout = mnOrderService.checkout(checkoutRequest.getUserId(), checkoutRequest.getProductId());
        if (null != checkout){
            return new ResponseEntity<>(checkout, HttpStatus.OK);
        }
        else return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
