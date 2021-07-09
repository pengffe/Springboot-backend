package com.sj.pte.modules.store.service;
/**
 * Created by TUTEHUB on 2021-07-08 15:21.
 * Copyright © 2021 TUTEHUB. All rights reserved.
 * ------------------------
 * Non-disclosure Terms
 * -------------------------
 * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
 * Developer fengpeng agrees with above terms.
 * Technique Support: jobyme88.com
 */

import com.sj.pte.modules.store.bean.MNProduct;
import com.sj.pte.modules.store.dao.MNProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @descrption
 */

@Service
public class MNProductServiceImpl implements MNProductService {

    @Autowired
    MNProductDao mnProductDao;

    @Override
    public MNProduct getProductById(String productId) {
        return mnProductDao.findById(MNProduct.class, productId);
    }

    @Override
    public List<MNProduct> getAllProduct() {
        return mnProductDao.findAll(MNProduct.class);
    }
}
