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
import com.sj.pte.modules.store.bean.MNProductCollect;
import com.sj.pte.modules.store.bean.MNTrolley;
import com.sj.pte.modules.store.dao.MNProductDao;
import com.sj.pte.modules.store.response.model.ProductBasicInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @descrption
 */

@Service
public class MNProductServiceImpl implements MNProductService {

    @Autowired
    MNProductDao mnProductDao;

    @Override
    public MNProduct getProductPreviewById(String productId) {
        MNProduct mnProduct = mnProductDao.findById(MNProduct.class, productId);
        if (null != mnProduct){
           preview(mnProduct);
           return mnProduct;
        }
        else {
            return null;
        }
    }

    @Override
    public List<MNProduct> getAllProductPreview() {
        List<MNProduct> mnProducts = mnProductDao.findAll(MNProduct.class);
        if (mnProducts.size() <= 0) return null;

        for (MNProduct mnProduct : mnProducts
            ) {
            preview(mnProduct);
        }
       return mnProducts;
    }

    private void preview(MNProduct mnProduct){
        Map<String, String> videoLesson = mnProduct.getVideoLesson();
        int i = 0;
        for (String key: videoLesson.keySet()
        ) {
            if (i != 0){
                videoLesson.replace(key, "");
            }
            i++;
        }
    }

    @Override
    public MNProduct getProductById(String productId) {
        return mnProductDao.findById(MNProduct.class, productId);
    }

    @Override
    public List<MNProduct> getAllProduct() {
        return mnProductDao.findAll(MNProduct.class);
    }

    @Override
    public List<ProductBasicInfoResponse> getCollectProduct(String userId) {
        MNProductCollect collect = mnProductDao.findById(MNProductCollect.class, userId);
        if (null != collect){
            return showProductBasicInfo(collect.getProductId());
        }
        else return null;
    }

    @Override
    public List<ProductBasicInfoResponse> showProductBasicInfo(List<String> productIds){
        List<ProductBasicInfoResponse> productBasicInfoResponses = new ArrayList<>();
        try {
            for (String id: productIds
            ) {
                MNProduct mnProduct = mnProductDao.findById(MNProduct.class, id);
                productBasicInfoResponses.add(new ProductBasicInfoResponse(mnProduct));
            }
            return productBasicInfoResponses;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
