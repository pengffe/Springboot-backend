package com.sj.pte.modules.store.controller;/**
 * Created by TUTEHUB on 2021-07-08 15:13.
 * Copyright © 2021 TUTEHUB. All rights reserved.
 * ------------------------
 * Non-disclosure Terms
 * -------------------------
 * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
 * Developer fengpeng agrees with above terms.
 * Technique Support: jobyme88.com
 */

import com.alibaba.fastjson.JSONObject;
import com.sj.pte.modules.store.bean.MNProduct;
import com.sj.pte.modules.store.response.model.ProductPreviewResponse;
import com.sj.pte.modules.store.service.MNProductServiceImpl;
import com.sj.pte.utils.upload.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @descrption
 */

@RestController
@RequestMapping("/product")
public class MNProductController {

    @Autowired
    UploadFileService uploadFileService;
    @Autowired
    MNProductServiceImpl mnProductService;

    @PostMapping("/{productName}/{productChapterName}/{teacher}")
    public ResponseEntity<JSONObject> addNewProduct(@RequestParam(value = "file") MultipartFile file,
                                                    @PathVariable String productName,
                                                    @PathVariable String teacher,
                                                    @PathVariable String productChapterName){
        MNProduct product = new MNProduct();
        product.setProductName(productName);
        product.setAuthor(teacher);
        return uploadFileService.saveVideoLesson(product, file, productChapterName);
    }

    @PostMapping("/{productId}/{productChapterName}")
    public ResponseEntity<JSONObject> addProduct(@RequestParam(value = "file") MultipartFile file,
                                                 @PathVariable String productId,
                                                 @PathVariable String productChapterName){
        MNProduct product = mnProductService.getProductById(productId);
        if (null == product){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return uploadFileService.saveVideoLesson(product, file, productChapterName);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductPreviewResponse> getProductPreview(@PathVariable String productId){
        ProductPreviewResponse productPreviewById = mnProductService.getProductPreviewById(productId);
        if (null != productPreviewById){
            return new ResponseEntity<>(productPreviewById, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<ProductPreviewResponse>> getAllProductPreview(){
        List<ProductPreviewResponse> allProductPreview = mnProductService.getAllProductPreview();
        if (allProductPreview.size() > 0){
            return new ResponseEntity<>(allProductPreview, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

//    @GetMapping("/{productId}")
//    public MNProduct previewProduct(@PathVariable String productId){
//        return mnProductService.getProductById(productId);
//    }

//    @GetMapping
//    public List<MNProduct> getAllProduct(){
//        return mnProductService.getAllProduct();
//    }
}
