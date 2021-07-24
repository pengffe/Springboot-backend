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

import com.sj.pte.modules.store.bean.MNProduct;
import com.sj.pte.modules.store.bean.MNOrderHistory;
import com.sj.pte.modules.store.request.model.CheckoutRequest;
import com.sj.pte.modules.store.response.model.OrderHistoryResponse;
import com.sj.pte.modules.store.service.MNOrderServiceImpl;
import com.sj.pte.modules.store.service.MNProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @descrption
 */

@RestController
@RequestMapping("/order")
public class MNOrderController {

    @Autowired
    MNOrderServiceImpl mnOrderService;
    
    @Autowired
    MNProductServiceImpl mnProductService;

    // 更新订单状态，是否已购买
    @PostMapping("")
    public ResponseEntity<?> checkoutOrder(@RequestBody CheckoutRequest checkoutRequest){
        MNOrderHistory checkout = mnOrderService.checkout(checkoutRequest.getUserId(), checkoutRequest.getProductId());
        if (null != checkout){
            return new ResponseEntity<>(checkout, HttpStatus.OK);
        }
        else return new ResponseEntity<>("FAIL TO CHECKOUT", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Obtain purchased products for user
    @GetMapping("/{userId}")
    public ResponseEntity<?> getAllPurchasedProduct(@PathVariable String userId){
        List<MNProduct> products = mnOrderService.showPurchasedProducts(userId);
        if (null != products){
            return new ResponseEntity<>(products, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("NO PURCHASED PRODUCTS", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<?> getOrderHistory(@PathVariable String userId){
        List<OrderHistoryResponse> orderHistory = new ArrayList<>();
        OrderHistoryResponse orderHistoryResponse = new OrderHistoryResponse();

        try {
            List<MNOrderHistory> orders = mnOrderService.showHistoryOrders(userId);
            for (MNOrderHistory order: orders
                 ) {
                orderHistoryResponse.setOrderId(order.getOrderId());
                List<String> productIds = order.getProductId();
                for (String id: productIds
                     ) {
                    MNProduct product = mnProductService.getProductById(id);
                    orderHistoryResponse.getProductInfo().put(product.getProductName(), product.getDescription());
                }
                orderHistory.add(orderHistoryResponse);
            }
            return new ResponseEntity<>(orderHistory, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("NO ORDER HISTORY", HttpStatus.NOT_FOUND);
        }
    }
}
