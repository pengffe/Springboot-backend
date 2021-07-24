package com.sj.pte.modules.store.response.model;/**
 * Created by TUTEHUB on 2021-07-15 13:50.
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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @descrption
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductBasicInfoResponse {
    private String productId;
    private String productName;
    private String author;


    public ProductBasicInfoResponse(MNProduct mnProduct){
        this.productId = mnProduct.getProductId();
        this.productName = mnProduct.getProductName();
        this.author = mnProduct.getAuthor();
    }
}
