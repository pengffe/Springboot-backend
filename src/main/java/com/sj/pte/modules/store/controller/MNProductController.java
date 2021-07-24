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

    /**
     * create a new lesson product
     */
    @PostMapping("/{productName}/{productChapterName}/{teacher}")
    public ResponseEntity<JSONObject> createNewProduct(@RequestParam(value = "file") MultipartFile file,
                                                    @PathVariable String productName,
                                                    @PathVariable String teacher,
                                                    @PathVariable String productChapterName){
        MNProduct product = new MNProduct();
        product.setProductName(productName);
        product.setAuthor(teacher);
        return uploadFileService.saveVideoLesson(product, file, productChapterName);
    }

    /**
     * add new video to product
     */
    @PostMapping("/{productId}/{productChapterName}")
    public ResponseEntity<JSONObject> addVideoToProduct(@RequestParam(value = "file") MultipartFile file,
                                                 @PathVariable String productId,
                                                 @PathVariable String productChapterName){
        MNProduct product = mnProductService.getProductById(productId);
        if (null == product){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return uploadFileService.saveVideoLesson(product, file, productChapterName);
    }


    /**
     * display the preview of products on home page
     */
    @GetMapping("/preview/{productId}")
    public ResponseEntity<?> getProductPreview(@PathVariable String productId){
        MNProduct productPreviewById = mnProductService.getProductPreviewById(productId);
        if (null != productPreviewById){
            return new ResponseEntity<>(productPreviewById, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("NO PRODUCTS OR WRONG PRODUCT-ID", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * display the preview of products on home page
     */
    @GetMapping("/preview")
    public ResponseEntity<?> getAllProductPreview(){
        List<MNProduct> allProductPreview = mnProductService.getAllProductPreview();
        if (null != allProductPreview){
            return new ResponseEntity<>(allProductPreview, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("NO PRODUCTS", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * display the basic info of collected products for user
     */
    @GetMapping("/collect")
    public ResponseEntity<?> getCollectProduct(){
        return null;
    }

    /**
     * Obtain content of product after customers purchase it
     */
    @GetMapping("/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable String productId){
        MNProduct mnProduct = mnProductService.getProductById(productId);
        if (null != mnProduct){
            return new ResponseEntity<>(mnProduct, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("NO PRODUCTS OR WRONG PRODUCT-ID", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public List<MNProduct> getAllProduct(){
        return mnProductService.getAllProduct();
    }
}
