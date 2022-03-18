package com.pengffe.pte.modules.question.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.mongodb.client.result.UpdateResult;
import com.pengffe.pte.modules.practice.bean.FilterStatusEnum;
import com.pengffe.pte.modules.question.bean.Question;
import com.pengffe.pte.modules.question.bean.QuestionRequest;
import com.pengffe.pte.modules.question.service.CheckService;
import com.pengffe.pte.modules.question.service.QuestionServiceImpl;
import com.pengffe.pte.utils.json.JSONUtil;
import com.pengffe.pte.utils.upload.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author pengffe  Email: pengffe@gmail.com
 * @descrption base class for question request
 * @date 26-08-2021
 */
@RestController
@RequestMapping("/question")
public class QuestionController {

    private ObjectMapper objectMapper = new ObjectMapper();

    private QuestionServiceImpl questionService;

    private UploadFileService uploadFileService;

    private CheckService checkService;

    @Autowired
    public void setCheckService(CheckService checkService) {
        this.checkService = checkService;
    }

    @Autowired
    public void setUploadFileService(UploadFileService uploadFileService) {
        this.uploadFileService = uploadFileService;
    }

    @Autowired
    public void setQuestionService(QuestionServiceImpl questionService) {
        this.questionService = questionService;
    }

    /***********
     * Create
     ***********/
    @PostMapping(value = "/local/{type}/{id}")
    public ResponseEntity<JSONObject> addOne(@PathVariable String type, @PathVariable int id) {

        String questionId = type + "-" + id;
        Question mnQuestion;

        ResponseEntity<JSONObject> jsonResponse = JSONUtil.readLocalJSONToQuestion(questionId);

        if (jsonResponse.getStatusCode().equals(HttpStatus.OK)){
            mnQuestion =(Question) jsonResponse.getBody().get("MSG");
            if (null != questionService.save(mnQuestion)){
                return jsonResponse;
            }
            else {
                JSONObject json = new JSONObject();
                json.put("MSG", "Fail to save or file exits. " + mnQuestion.getQuestionId());
                return new ResponseEntity<>(json, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else {
            return jsonResponse;
        }
    }

    @PostMapping(value = "/local/{type}")
    public ResponseEntity<JSONObject> addAll(@PathVariable String type) {
        JSONObject json = new JSONObject();
        int i = 228;
        while (true) {
            ResponseEntity<JSONObject> jsonObjectResponseEntity = addOne(type, i);
            if (!jsonObjectResponseEntity.getStatusCode().equals(HttpStatus.OK)){
                json.put(type + i, jsonObjectResponseEntity.getBody().get("MSG"));
            }
            if (jsonObjectResponseEntity.getStatusCode().equals(HttpStatus.NOT_FOUND)){
                return new ResponseEntity<>(json, HttpStatus.OK);
            }
            i++;
        }
    }

    @PostMapping(value = "/{type}")
    public ResponseEntity<String> addOneByPostman(@RequestBody QuestionRequest mnQuestionRequest, @PathVariable String type){
        Question mnQuestion = questionService.readRequestJSONToObject(type, mnQuestionRequest);
        if (null == mnQuestion){
            return new ResponseEntity<>("URL TYPE IS NOT SAME AS JSON TYPE", HttpStatus.CONFLICT);
        }
        if (null != questionService.save(mnQuestion)){
            System.out.println(mnQuestionRequest.toString());
            return new ResponseEntity<>("SUCCESS TO ADD", HttpStatus.OK);
        }
        else return new ResponseEntity<>("INSTANCE EXITS", HttpStatus.CREATED);
    }

    /***********
     * Read
     ***********/
    @GetMapping(value = "/{type}/{id}")
    public Question findById(@PathVariable String type, @PathVariable String id) {
        String questionId = type + "-" + id;
        return questionService.findById(checkService.checkType(type).getClass(), questionId);
    }

//    @GetMapping(value = "/{type}")
//    public List findAll(@PathVariable String type) {
//        return questionService.findAll(checkService.checkType(type).getClass());
//    }

    @GetMapping(value = "/{userId}/{type}/{source}/{frequency}/{practiceStatus}/{collectStatus}")
    public Page findAllByFilter(@PathVariable String userId,
                                @PathVariable String type,
                                @PathVariable FilterStatusEnum source,
                                @PathVariable FilterStatusEnum frequency,
                                @PathVariable FilterStatusEnum practiceStatus,
                                @PathVariable FilterStatusEnum collectStatus,
                                @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "0")  int pageSize,
                                @RequestParam(value = "sortType", defaultValue = "ASC")  String sortType){
        return questionService.findAllByFilter(checkService.checkType(type).getClass(), userId, type,
                source, frequency, practiceStatus, collectStatus,
                pageNum, pageSize, sortType);
    }

    @GetMapping(value = "/{type}")
    public Page findAllByPage(@PathVariable String type,
                              @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                              @RequestParam(value = "pageSize", defaultValue = "0")  int pageSize,
                              @RequestParam(value = "sortType", defaultValue = "ASC")  String sortType) {
        return questionService.findAllByPage(checkService.checkType(type).getClass(), pageNum, pageSize, sortType);
    }

    @GetMapping(value = "/count/{type}")
    public Long findCountByType(@PathVariable String type){
        return questionService.findCount(checkService.checkType(type).getClass());
    }

    /***********
     * Update
     ***********/
    @PutMapping(value = "/{type}/{id}")
    public ResponseEntity<JSONObject> updateFileById(@PathVariable String type,
                                                     @PathVariable String id,
                                                     @RequestParam(value = "file", required = false) MultipartFile file){
        String questionId = type + "-" + id;
        return uploadFileService.saveFileToDisk(questionId, file, null);
    }

    @PatchMapping(value = "/{type}/{id}")
    public UpdateResult updateAttribute(@RequestBody QuestionRequest mnQuestionRequest,
                                        @PathVariable String type,
                                        @PathVariable String id){
        String questionId = type + "-" + id;
        return questionService.updateById(checkService.checkType(type).getClass(), questionId, mnQuestionRequest);
    }

//    @PatchMapping(path = "/{type}/{id}", consumes = "application/json-patch+json")
//    public ResponseEntity update(@PathVariable String type,
//                                              @PathVariable String id,
//                                              @RequestBody JsonPatch patch){
//        String questionId = type + "-" + id;
//        try {
//            Question mnQuestion = questionService.findById(checkService.checkType(questionId).getClass(), questionId);
//            if (null == mnQuestion){
//                return new ResponseEntity<>("QUESTION NOT FOUND", HttpStatus.NOT_FOUND);
//            }
//
//            Question questionPatched = applyPatchToQuestion(patch, mnQuestion);
//            UpdateResult update = questionService.update(questionPatched);
//            return new ResponseEntity<>(update, HttpStatus.OK);
//        } catch (JsonPatchException | JsonProcessingException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

    private Question applyPatchToQuestion(JsonPatch patch, Question targetQuestion) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(targetQuestion, JsonNode.class));
        return objectMapper.treeToValue(patched, targetQuestion.getClass());
    }

    /***********
     * Delete
     ***********/
    @DeleteMapping("/{type}/{id}")
    public void deleteById(@PathVariable String type, @PathVariable String id){
        String questionId = type + "-" + id;
        questionService.deleteById(checkService.checkType(type).getClass(), questionId);
    }
}