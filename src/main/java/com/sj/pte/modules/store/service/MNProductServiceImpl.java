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
import com.sj.pte.modules.store.response.model.ProductPreviewResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @descrption
 */

@Service
public class MNProductServiceImpl implements MNProductService {

    @Autowired
    MNProductDao mnProductDao;

    @Override
    public ProductPreviewResponse getProductPreviewById(String productId) {
        MNProduct mnProduct = mnProductDao.findById(MNProduct.class, productId);
        if (null != mnProduct){
            return new ProductPreviewResponse(mnProduct);
        }
        else {
            return null;
        }
    }

    @Override
    public List<ProductPreviewResponse> getAllProductPreview() {
        List<ProductPreviewResponse> previews = new ArrayList<>();
        List<MNProduct> mnProducts = mnProductDao.findAll(MNProduct.class);
        if (mnProducts.size() > 0){
            for (MNProduct item: mnProducts
                 ) {
                previews.add(new ProductPreviewResponse(item));
            }
        }
       return previews;
    }

    @Override
    public MNProduct getProductById(String productId) {
        return mnProductDao.findById(MNProduct.class, productId);
    }

    @Override
    public List<MNProduct> getAllProduct() {
        return mnProductDao.findAll(MNProduct.class);
    }
}
