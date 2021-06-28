package com.sj.pte.modules.jobApplication;/**
 * Created by TUTEHUB on 2021-06-28 15:22.
 * Copyright © 2021 TUTEHUB. All rights reserved.
 * ------------------------
 * Non-disclosure Terms
 * -------------------------
 * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
 * Developer fengpeng agrees with above terms.
 * Technique Support: jobyme88.com
 */

import com.alibaba.fastjson.JSONObject;
import com.sj.pte.utils.upload.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @descrption
 */

@RestController
@RequestMapping("/apply")
public class MNApplyController {

    private UploadFileService uploadFileService;

    @Autowired
    public void setUploadFileService(UploadFileService uploadFileService) {
        this.uploadFileService = uploadFileService;
    }

    @PostMapping
    public ResponseEntity<JSONObject> updateFileById(MNApplicant applicant,
                                                     @RequestParam(value = "file", required = false) MultipartFile file) {
        System.out.println(applicant.toString());
        return uploadFileService.saveResumeToDisk(applicant, file);
//        return null;
    }
}
