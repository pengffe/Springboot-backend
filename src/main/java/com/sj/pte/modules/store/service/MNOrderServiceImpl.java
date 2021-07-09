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

import cn.hutool.core.util.RandomUtil;
import com.mongodb.client.result.UpdateResult;
import com.sj.pte.modules.store.bean.MNOrder;
import com.sj.pte.modules.store.bean.MNOrderHistory;
import com.sj.pte.modules.store.dao.MNOrderDao;
import com.sj.pte.modules.store.dao.MNProductDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Random;

/**
 * @descrption
 */

public class MNOrderServiceImpl implements MNOrderService {

    @Autowired
    MNOrderDao mnOrderDao;
    @Autowired
    MNProductDao mnProductDao;

    @Override
    public MNOrder addProductsToTrolley(String userId, List<String> productIds) {
        if (null == mnOrderDao.findById(MNOrder.class, userId)){
            MNOrder mnOrder = new MNOrder();
            mnOrder.setUserId(userId);
            mnOrder.getProductId().addAll(productIds);
            return mnOrderDao.save(mnOrder);
        }
        else {
            mnOrderDao.updateProductIdById(MNOrder.class, userId, productIds, true);
            return mnOrderDao.findById(MNOrder.class, userId);
        }
    }

    @Override
    public List<MNOrder> showProductsInTrolley(String userId) {
        return null;
    }

    @Override
    public MNOrderHistory checkout(String userId, List<String> productIds) {
        /**
         * 1, remove the ids from trolley
         * 2, create order ID
         * 3, add the ids to history
         */
        mnOrderDao.updateProductIdById(MNOrder.class, userId, productIds, false);
        MNOrderHistory mnOrderHistory = new MNOrderHistory();
        mnOrderHistory.setOrderId(RandomUtil.randomString(16));
        mnOrderHistory.setUserId(userId);
        mnOrderHistory.setProductId(productIds);

        return mnOrderDao.save(mnOrderHistory);
    }

    @Override
    public List<MNOrder> showHistoryOrders(String userId) {
        return null;
    }
}
