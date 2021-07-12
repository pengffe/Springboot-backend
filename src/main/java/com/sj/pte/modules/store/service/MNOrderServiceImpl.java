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
import com.sj.pte.modules.store.bean.MNTrolley;
import com.sj.pte.modules.store.bean.MNOrderHistory;
import com.sj.pte.modules.store.dao.MNOrderDao;
import com.sj.pte.modules.store.dao.MNProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @descrption
 */

@Service
public class MNOrderServiceImpl implements MNOrderService {

    @Autowired
    MNOrderDao mnOrderDao;
    @Autowired
    MNProductDao mnProductDao;

    @Override
    public MNTrolley addProductsToTrolley(String userId, List<String> productIds) {
        if (null == mnOrderDao.findById(MNTrolley.class, userId)){
            MNTrolley mnTrolley = new MNTrolley();
            mnTrolley.setUserId(userId);
            mnTrolley.getProductId().addAll(productIds);
            return mnOrderDao.save(mnTrolley);
        }
        else {
            mnOrderDao.updateProductIdById(MNTrolley.class, userId, productIds, true);
            return mnOrderDao.findById(MNTrolley.class, userId);
        }
    }

    @Override
    public List<String> showProductsInTrolley(String userId) {
        MNTrolley mnTrolley = mnOrderDao.findById(MNTrolley.class, userId);
        if (null != mnTrolley){
            return mnTrolley.getProductId();
        }
        else return null;
    }

    @Override
    public MNOrderHistory checkout(String userId, List<String> productIds) {
        /**
         * 1, remove the ids from trolley
         * 2, create order ID
         * 3, add the ids to history
         */
        mnOrderDao.updateProductIdById(MNTrolley.class, userId, productIds, false);
        MNOrderHistory mnOrderHistory = new MNOrderHistory();
        mnOrderHistory.setOrderId(RandomUtil.randomString(16));
        mnOrderHistory.setUserId(userId);
        mnOrderHistory.setProductId(productIds);

        return mnOrderDao.save(mnOrderHistory);
    }

    @Override
    public List<MNTrolley> showHistoryOrders(String userId) {
        return null;
    }
}
