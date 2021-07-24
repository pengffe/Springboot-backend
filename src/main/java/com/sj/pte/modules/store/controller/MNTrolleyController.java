package com.sj.pte.modules.store.controller;/**
 * Created by TUTEHUB on 2021-07-13 12:44.
 * Copyright © 2021 TUTEHUB. All rights reserved.
 * ------------------------
 * Non-disclosure Terms
 * -------------------------
 * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
 * Developer fengpeng agrees with above terms.
 * Technique Support: jobyme88.com
 */

import com.sj.pte.modules.store.bean.MNTrolley;
import com.sj.pte.modules.store.response.model.ProductBasicInfoResponse;
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
@RequestMapping("trolley")
public class MNTrolleyController {

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

    /**
     * show product info in the trolley, including product name, description, author
     */
    @GetMapping("/{userId}")
    public ResponseEntity<?> showTrolley(@PathVariable String userId){
        List<ProductBasicInfoResponse> mnProductIds = mnOrderService.showProductsInTrolley(userId);
        if (null != mnProductIds){
            return new ResponseEntity<>(mnProductIds, HttpStatus.OK);
        }
        else return new ResponseEntity<>("NO PRODUCT IN TROLLEY", HttpStatus.NOT_FOUND);
    }
}
