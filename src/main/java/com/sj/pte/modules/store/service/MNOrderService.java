package com.sj.pte.modules.store.service;/**
 * Created by TUTEHUB on 2021-07-09 14:45.
 * Copyright © 2021 TUTEHUB. All rights reserved.
 * ------------------------
 * Non-disclosure Terms
 * -------------------------
 * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
 * Developer fengpeng agrees with above terms.
 * Technique Support: jobyme88.com
 */

import com.mongodb.client.result.UpdateResult;
import com.sj.pte.modules.store.bean.MNOrder;
import com.sj.pte.modules.store.bean.MNOrderHistory;

import java.util.List;

/**
 * @descrption
 */

public interface MNOrderService {

    /**
     * 用户添加订单到购物车
     */
    MNOrder addProductsToTrolley(String userId, List<String>  productIds);

    /**
     * 显示购物车订单清单
     */
    List<MNOrder> showProductsInTrolley(String userId);

    /**
     *  更新订单信息，是否已购买
     */
    MNOrderHistory checkout(String userId, List<String> productIds);

    /**
     * 显示历史订单
     */
    List<MNOrder> showHistoryOrders(String userId);
}
