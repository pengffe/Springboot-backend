package com.pengffe.pte.utils.upload;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.result.UpdateResult;
import com.pengffe.pte.modules.jobApplication.Applicant;
import com.pengffe.pte.modules.jobApplication.ApplicantDao;
import com.pengffe.pte.modules.practice.bean.Practice;
import com.pengffe.pte.modules.practice.dao.PracticeDao;
import com.pengffe.pte.modules.question.dao.QuestionDao;
import com.pengffe.pte.modules.question.service.CheckServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @descrption
 */

@Service
public class UploadFileService {

    @Value("${question.filePath}")
    private String questionFilePath;

    @Value("${question.fileURLPath}")
    private String questionFileURLPath;

    @Value("${user.filePath}")
    private String userFilePath;

    @Value("${user.fileURLPath}")
    private String userFileURLPath;

    @Value("${applicant.resumePath}")
    private String resumePath;

    @Value("${applicant.resumeURLPath}")
    private String resumeURLPath;

    @Value("${product.productPath}")
    private String productPath;

    @Value("${product.productURLPath}")
    private String productURLPath;

    @Autowired
    PracticeDao practiceDao;

    @Autowired
    ApplicantDao applicantDao;

//    @Autowired
//    ProductDao mnProductDao;

    private QuestionDao questionDao;

    private CheckServiceImpl checkService;

    @Autowired
    public void setQuestionDao(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Autowired
    public void setcheckService(CheckServiceImpl checkService) {
        this.checkService = checkService;
    }

    /************
     *
     * 将前端传来的文件保存到服务器磁盘中，并将该路径保存到数据库相应的属性下。
     * @param questionId
     * @param file
     * @return
     */
    public ResponseEntity<JSONObject> saveFileToDisk(String questionId, MultipartFile file, String userId) {

        JSONObject json = null;
        try {
            json = new JSONObject();
            if(file==null){
                json.put("STATUS", "ERROR");
                json.put("MSG", "Fail to upload，upload file is null.");
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
                json.put("MSG", "Illegal file format.");
                return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
            }
            //保存的文件名
            String dFileName = questionId + "-" + UUID.randomUUID() + substring;

            //服务器磁盘存储路径
            String path;
            //服务器url访问路径
            String URLPath;
            if (null != userId){
                path = userFilePath + userId + "/";
                URLPath = userFileURLPath + userId + "/" + dFileName;
            }
            else{
                path = questionFilePath  +  questionId.split("-")[0] + "/";
                URLPath = questionFileURLPath + questionId.split("-")[0]  + "/" + dFileName;
            }

            //生成保存文件
            File uploadFile = new File(path + dFileName);
            System.out.println(uploadFile);
            if(!uploadFile.getParentFile().exists()){
                //注意，判断父级路径是否存在
                boolean mkdirs = uploadFile.getParentFile().mkdirs();
                if (!mkdirs){
                    json.put("RESULT", "FAIL TO CREATE FOLDER");
                }
            }

            System.out.println("uploadFile:" + uploadFile);
            System.out.println("uploadURLFile:" + URLPath);


            //将上传文件保存到路径
            file.transferTo(uploadFile);
            /**
             * 将生成文件路径更新到db中相对应的题目表下
             */
            UpdateResult updateResult;
            Practice mnPractice;

            /**
             * First, check whether userId is null, if null, means not a practice;
             * Then, check whether it is a lesson file(image, audio, or video);
             * Last, it will be a image or audio, which is a part of question content
             */
            if (null != userId){
                mnPractice = practiceDao.save(new Practice(questionId, URLPath, userId));
                json.put("Result", mnPractice.toString());
            }
            else if (originalFilename.contains("lesson")){
                System.out.println("save lesson: " + originalFilename);
                updateResult = questionDao.updateLessonById(checkService.checkType(questionId).getClass(), questionId, URLPath);
                json.put("Result", updateResult.toString());
            }
            else if (substring.equals(".mp3")){
                System.out.println("save audio: " + substring);
                updateResult = questionDao.updateAudioById(checkService.checkType(questionId).getClass(), questionId, URLPath);
                json.put("Result", updateResult.toString());
            }
            else {
                System.out.println("save image: " + substring);
                updateResult = questionDao.updateImageById(checkService.checkType(questionId).getClass(), questionId, URLPath);
                json.put("Result", updateResult.toString());
            }


            json.put("STATUS", "200");
            json.put("MSG", "UPLOAD SUCCESS.");
            return new ResponseEntity<>(json, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            json.put("STATUS", "ERROR");
            json.put("MSG", "FAIL TO SAVE FILE(need to create folder in advance)");
            return new ResponseEntity<>(json, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /************
     *
     * 将前端传来的文件保存到服务器磁盘中，并将该路径保存到数据库相应的属性下。
     * @return
     */
    public ResponseEntity<JSONObject> saveResumeToDisk(Applicant applicant, MultipartFile file){

        ResponseEntity<JSONObject> checkResult = checkFile(file);
        //获取后缀
        String substring;
        if (checkResult.getStatusCode().equals(HttpStatus.OK)){
            substring = checkResult.getBody().getString("MSG");
        }
        else {
            return checkResult;
        }

        String applicantName = applicant.getFirstName() + " " + applicant.getLastName();
        //保存的文件名
        String dFileName = applicantName + "-" + UUID.randomUUID() + substring;

        //服务器磁盘存储路径
        String path = resumePath + applicantName + "/";
        //服务器url访问路径
        String URLPath = resumeURLPath + applicantName + "/" + dFileName;

        JSONObject json = new JSONObject();

        //生成保存文件
        File uploadFile = new File(path + dFileName);
        if(!uploadFile.getParentFile().exists()){
            //注意，判断父级路径是否存在
            boolean mkdirs = uploadFile.getParentFile().mkdirs();
            if (!mkdirs){
                json.put("RESULT", "FAIL TO CREATE FOLDER");
            }
        }

        //将上传文件保存到路径
        try {
            file.transferTo(uploadFile);
            applicant.setCVPath(URLPath);
        } catch (IOException e) {
            json.put("STATUS", "ERROR");
            json.put("MSG", "FAIL TO SAVE FILE(need to create folder in advance)");
            return new ResponseEntity<>(json, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Applicant result = applicantDao.save(applicant);
        json.put("Result", result.toString());
        json.put("STATUS", "200");
        json.put("MSG", "APPLICATION SUCCESS.");
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    /**
     * 将视频课程存入数据库以及磁盘中
     */
//    public ResponseEntity<JSONObject> saveVideoLesson(Product mnProduct, MultipartFile file, String chapterName){
//        ResponseEntity<JSONObject> checkResult = checkFile(file);
//        //获取后缀
//        String substring;
//        if (checkResult.getStatusCode().equals(HttpStatus.OK)){
//            substring = checkResult.getBody().getString("MSG");
//        }
//        else {
//            return checkResult;
//        }
//
//        JSONObject json = new JSONObject();
//        String teacherName = mnProduct.getAuthor();
//        //保存的文件名
//        String dFileName = teacherName + "-" + chapterName + "-" + UUID.randomUUID() + substring;
//
//        //服务器磁盘存储路径
//        String path = productPath;
//        //服务器url访问路径
//        String URLPath = productURLPath  + dFileName;
//
//
//        //生成保存文件
//        File uploadFile = new File(path + dFileName);
//        if(!uploadFile.getParentFile().exists()){
//            //注意，判断父级路径是否存在
//            boolean mkdirs = uploadFile.getParentFile().mkdirs();
//            if (!mkdirs){
//                json.put("RESULT", "FAIL TO CREATE FOLDER");
//            }
//        }
//
//        //将上传文件保存到路径
//        try {
//            file.transferTo(uploadFile);
//            Product result;
//            String productId = mnProduct.getProductId();
//            if (null == productId){
//                mnProduct.setProductId(RandomUtil.randomString(8));
//                mnProduct.getVideoLesson().put(chapterName, URLPath);
//                result = mnProductDao.save(mnProduct);
//            }
//            else {
//                mnProduct.getVideoLesson().put(chapterName, URLPath);
//                mnProductDao.updateVideoLessonById(mnProduct);
//                result = mnProductDao.findById(Product.class, productId);
//            }
//            json.put("Result", result.toString());
//            json.put("STATUS", "200");
//            json.put("MSG", "SUCCESS TO ADD PRODUCT.");
//            return new ResponseEntity<>(json, HttpStatus.OK);
//        } catch (IOException e) {
//            json.put("STATUS", "ERROR");
//            json.put("MSG", "FAIL TO SAVE FILE(need to create folder in advance)");
//            return new ResponseEntity<>(json, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    private ResponseEntity<JSONObject> checkFile(MultipartFile file){

        JSONObject json = new JSONObject();
        if(file==null){
            json.put("STATUS", "ERROR");
            json.put("MSG", "Fail to upload，upload file is null.");
            return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
        }

        //获取上传文件名,包含后缀
        String originalFilename = file.getOriginalFilename();
        //获取后缀
        String substring;
        try {
            substring = originalFilename.substring(originalFilename.lastIndexOf("."));
            json.put("STATUS", "OK");
            json.put("MSG", substring);
            return new ResponseEntity<>(json, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("STATUS", "ERROR");
            json.put("MSG", "Illegal file format.");
            return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
        }

    }
}
