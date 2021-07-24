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

import com.sj.pte.modules.store.bean.MNProduct;
import com.sj.pte.modules.store.bean.MNTrolley;
import com.sj.pte.modules.store.bean.MNOrderHistory;
import com.sj.pte.modules.store.response.model.ProductBasicInfoResponse;

import java.util.List;

/**
 * @descrption
 */

public interface MNOrderService {

    /**
     * add product to trolley; 用户添加订单到购物车
     */
    MNTrolley addProductsToTrolley(String userId, List<String>  productIds);

    /**
     * display products in trolley; 显示购物车订单清单
     */
    List<ProductBasicInfoResponse> showProductsInTrolley(String userId);

    /**
     *  update order info, whether is purchased; 更新订单信息，是否已购买
     */
    MNOrderHistory checkout(String userId, List<String> productIds);

    /**
     * display order history; 显示订单历史
     */
    List<MNOrderHistory> showHistoryOrders(String userId);

    /**
     * display all purchased products for user； 显示用户所有购买过的课程
     */
    List<MNProduct> showPurchasedProducts(String userId);
}
