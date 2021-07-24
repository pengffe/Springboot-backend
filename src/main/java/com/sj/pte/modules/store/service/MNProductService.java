package com.sj.pte.modules.store.service;

import com.sj.pte.modules.store.bean.MNProduct;
import com.sj.pte.modules.store.response.model.ProductBasicInfoResponse;

import java.util.List;

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
public interface MNProductService {

    MNProduct getProductPreviewById(String productId);
    List<MNProduct> getAllProductPreview();
    MNProduct getProductById(String productId);
    List<MNProduct> getAllProduct();

    /**
     * display products in trolley and collect; 显示购物车订单清单
     */
    List<ProductBasicInfoResponse> getCollectProduct(String userId);

    List<ProductBasicInfoResponse> showProductBasicInfo(List<String> productIds);
}
