package com.sj.pte.modules.store.response.model;/**
 * Created by TUTEHUB on 2021-07-12 15:00.
 * Copyright © 2021 TUTEHUB. All rights reserved.
 * ------------------------
 * Non-disclosure Terms
 * -------------------------
 * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
 * Developer fengpeng agrees with above terms.
 * Technique Support: jobyme88.com
 */

import com.sj.pte.modules.store.bean.MNProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @descrption to display the preview of the products, only first video can play, others only display the video name
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductPreviewResponse {
    private String productId;
    private String productName;
    private String price;
    private List<String> preview = new ArrayList<>();

    public ProductPreviewResponse(MNProduct mnProduct){
        this.productId = mnProduct.getProductId();
        this.productName = mnProduct.getProductName();
        this.price = mnProduct.getPrice();
        this.preview.add(mnProduct.getProductPath().get(0));
        this.preview.addAll(mnProduct.getProductChapterName());
    }
}
