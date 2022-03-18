package com.pengffe.pte.modules.jobApplication;

import com.alibaba.fastjson.JSONObject;
import com.pengffe.pte.utils.upload.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @descrption
 */

@RestController
@RequestMapping("/apply")
public class ApplyController {

    private UploadFileService uploadFileService;

    @Autowired
    public void setUploadFileService(UploadFileService uploadFileService) {
        this.uploadFileService = uploadFileService;
    }

    @PostMapping
    public ResponseEntity<JSONObject> updateFileById(Applicant applicant,
                                                     @RequestParam(value = "file", required = false) MultipartFile file) {
        System.out.println(applicant.toString());
        return uploadFileService.saveResumeToDisk(applicant, file);
//        return null;
    }
}
