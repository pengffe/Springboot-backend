package com.sj.pte.modules.question.service;/**
 * Created by TUTEHUB on 2021/6/7 4:21 PM.
 * Copyright © 2021 TUTEHUB. All rights reserved.
 * ------------------------
 * Non-disclosure Terms
 * -------------------------
 * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
 * Developer dph agrees with above terms.
 * Technique Support: jobyme88.com
 * Contact: noreply@fengcaoculture.com
 */


import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.sj.pte.modules.question.bean.*;
import com.sj.pte.modules.question.dao.MNQuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


/**
 * @description
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    @Value("${question.filePath}")
    private String filePath;

    private MNQuestionDao mnQuestionDao;

    private CheckDurationServiceImpl checkDurationService;

    @Autowired
    public void setMnQuestionDao(MNQuestionDao mnQuestionDao) {
        this.mnQuestionDao = mnQuestionDao;
    }
    public MNQuestionDao getMnQuestionDao() {
        return mnQuestionDao;
    }

    @Autowired
    public void setCheckDurationService(CheckDurationServiceImpl checkDurationService) {
        this.checkDurationService = checkDurationService;
    }
    public CheckDurationServiceImpl getCheckDurationService() {
        return checkDurationService;
    }






    public MNQuestion checkType(String questionType) {
        String type  = questionType;
        if (questionType.contains("-")){
            type = questionType.split("-")[0];
        }

        switch (type) {
            /**
             * speaking
             */
            case ("ra"):
                return new MNRA();
            case ("rs"):
                return new MNRS();
            case ("rl"):
                return new MNRL();
            case ("di"):
                return new MNDI();
            case ("asq"):
                return new MNASQ();

            /**
             * reading
             */
            case ("rmcs"):
                return new MNRMCS();
            case("rmcm"):
                return new MNRMCM();
            case("ro"):
                return new MNRO();
            case("rfib"):
                return new MNRFIB();
            case("rwfib"):
                return new MNRWFIB();

            /**
             * listening
             */
            case("fib"):
                return new MNFIB();
            case("hcs"):
                return new MNHCS();
            case("hiw"):
                return new MNHIW();
            case("mcm"):
                return new MNMCM();
            case("mcs"):
                return new MNMCS();
            case("smw"):
                return new MNSMW();
            case("sst"):
                return new MNSST();
            case("wfd"):
                return new MNWFD();

            /**
             * writing
             */
            case("swt"):
                return new MNSWT();
            case("we"):
                return new MNWE();
        }
        return new MNQuestion();
    }



    /***********
     * Create
     ***********/
    @Override
    public <T extends MNQuestion> T save(T t) {
        if (null == t){
            System.out.println("Fail to find the file!");
            return null;
        }

        if (null != mnQuestionDao.findById(t.getClass(), t.getQuestionId())){
            System.out.println("Instance exits");
            return null;
        }

        else {
            return mnQuestionDao.save(t);
        }
    }


    /***********
     * Read
     ***********/
    @Override
    public <T> T findById(Class<T> tCLass, String questionId){
        return mnQuestionDao.findById(tCLass, questionId);
    }

    @Override
    public <T> List<T> findAll(Class<T> tClass) {
        List list = mnQuestionDao.findAll(tClass);
        return checkDurationService.checkDuration(list);
    }



    /***********
     * Delete
     ***********/
    @Override
    public <T> DeleteResult deleteById(Class<T> tCLass, String id) {
        return mnQuestionDao.deleteById(tCLass, id);
    }


    /************
     *
     * 将前端传来的文件保存到服务器磁盘中，并将该路径保存到数据库相应的属性下。
     * @param questionId
     * @param file
     * @return
     */
    @Override
    public ResponseEntity<JSONObject> saveFileToDisk(String questionId, MultipartFile file) {

        JSONObject json = null;
        try {
            json = new JSONObject();
            if(file==null){
                json.put("STATUS", "ERROR");
                json.put("MSG", "上传失败，上传图片数据为空");
                return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
            }

            //获取上传文件名,包含后缀
            String originalFilename = file.getOriginalFilename();
            //获取后缀
            String substring;
            try {
                substring = originalFilename.substring(originalFilename.lastIndexOf("."));
            } catch (Exception e) {
                e.printStackTrace();
                json.put("STATUS", "ERROR");
                json.put("MSG", "文件格式非法");
                return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
            }
            //保存的文件名
            String dFileName = UUID.randomUUID()+substring;

            //保存路径
            //springboot 默认情况下只能加载 resource文件夹下静态资源文件
            String path = filePath + questionId.split("-")[0] + "/" + questionId + "-";

            //生成保存文件
            String answerPath = path + dFileName;
            File uploadFile = new File(answerPath);
            System.out.println(uploadFile);
            if(!uploadFile.getParentFile().exists()){
                //注意，判断父级路径是否存在
                uploadFile.getParentFile().mkdirs();
            }

            System.out.println("uploadFile:" + uploadFile);

            //将上传文件保存到路径
            file.transferTo(uploadFile);
            /**
             * 将生成文件路径更新到db中相对应的题目表下
             */
            UpdateResult updateResult = mnQuestionDao.updateImageById(checkType(questionId).getClass(), questionId, uploadFile.toString());


            json.put("STATUS", "200");
            json.put("MSG", "上传图片成功");
            json.put("Result", updateResult);
            return new ResponseEntity<>(json, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            json.put("STATUS", "ERROR");
            json.put("MSG", "FAIL TO SAVE FILE(need to create folder in advance)");
            return new ResponseEntity<>(json, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
