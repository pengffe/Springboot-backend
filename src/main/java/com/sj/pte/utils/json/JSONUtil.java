package com.sj.pte.utils.json;/**
 * Created by TUTEHUB on 2021/6/7 3:47 AM.
 * Copyright © 2021 TUTEHUB. All rights reserved.
 * ------------------------
 * Non-disclosure Terms
 * -------------------------
 * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
 * Developer dph agrees with above terms.
 * Technique Support: jobyme88.com
 * Contact: noreply@fengcaoculture.com
 */


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.sj.pte.modules.question.bean.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * @description
 */
public class JSONUtil {

    /**
     *
     * @param fileName
     * put in resources
     * @return String
     * @throws IOException
     * @errors file not exists, read to string failed
     */
    public static String readLocalJSONToString(String fileName) throws IOException {
        File resource = new ClassPathResource(fileName).getFile();
        String jsonStr = new String(Files.readAllBytes(resource.toPath()));
        return jsonStr;
    }



    /**
     *
     * @param fileName
     * @return
     * @throws IOException
     * @errors json parse failed, [TODO] how to throw?
     */
    public static JSONObject readLocalJSONToObject(String fileName) throws IOException{
        String jsonStr = readLocalJSONToString(fileName);
        JSONObject jsonObj = JSON.parseObject(jsonStr);
        return jsonObj;
    }


    /**
     *
     * @param questionId
     * @return
     * @throws IOException
     */
    public static ResponseEntity<JSONObject> readLocalJSONToQuestion(String questionId) {
        String questionType = questionId.split("-")[0];
        String filePath = questionType + "/" + questionId + ".json";


        // error handling
        JSONObject jsonObj = null;
        JSONObject jsonResponse = new JSONObject();
        try {
            jsonObj = readLocalJSONToObject(filePath);
        } catch (IOException e) {
            jsonResponse.put("STATUS", "ERROR");
            jsonResponse.put("MSG", filePath + " not found.");
            return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
        } catch (JSONException e){
            jsonResponse.put("STATUS", "ERROR");
            jsonResponse.put("MSG", filePath + " : Illegal json format");
            return new ResponseEntity<>(jsonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (Exception e) {
            jsonResponse.put("STATUS", "ERROR");
            jsonResponse.put("MSG", filePath + " : " + e.toString());
            return new ResponseEntity<>(jsonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }



        // unique
        MNQuestion mnQuestion = new MNQuestion();
        String audioPath;
        String imagePath;
        String question;
        List<String> options;
        List<String> sampleAnswers;
        switch (questionType) {
            /**
             * speaking
             */
            case ("ra"):
                mnQuestion = new MNRA();
                break;
            case ("rs"):
                audioPath = ((String) jsonObj.get("audioPath"));
                mnQuestion = new MNRS(audioPath);
                break;
            case ("rl"):
                audioPath = ((String) jsonObj.get("audioPath"));
                imagePath = ((String) jsonObj.get("imagePath"));
                mnQuestion = new MNRL(audioPath, imagePath);
                break;
            case ("di"):
                imagePath = ((String) jsonObj.get("imagePath"));
                sampleAnswers = ((List<String>) jsonObj.get("sampleAnswers"));
                mnQuestion = new MNDI(imagePath, sampleAnswers);
                break;
            case ("asq"):
                audioPath = ((String) jsonObj.get("audioPath"));
                mnQuestion = new MNASQ(audioPath);
                break;

            /**
             * reading
             */
            case ("rmcs"):
                question = ((String) jsonObj.get("question"));
                options = ((List<String>) jsonObj.get("options"));
                mnQuestion = new MNRMCS(question, options);
                break;
            case("rmcm"):
                question = ((String) jsonObj.get("question"));
                options = ((List<String>) jsonObj.get("options"));
                mnQuestion = new MNRMCM(question, options);
                break;
            case("ro"):
                mnQuestion = new MNRO();
                break;
            case("rfib"):
                options = ((List<String>) jsonObj.get("options"));
                mnQuestion = new MNRFIB(options);
                break;
            case("rwfib"):
                mnQuestion = new MNRWFIB();
                break;

            /**
             * listening
             */
            case("fib"):
                audioPath = ((String) jsonObj.get("audioPath"));
                mnQuestion = new MNFIB(audioPath);
                break;
            case("hcs"):
                audioPath = ((String) jsonObj.get("audioPath"));
                options = ((List<String>) jsonObj.get("options"));
                mnQuestion = new MNHCS(audioPath, options);
                break;
            case("hiw"):
                audioPath = ((String) jsonObj.get("audioPath"));
                mnQuestion = new MNHIW(audioPath);
                break;
            case("wfd"):
                audioPath = ((String) jsonObj.get("audioPath"));
                mnQuestion = new MNWFD(audioPath);
                break;
            case("mcm"):
                question = ((String) jsonObj.get("question"));
                audioPath = ((String) jsonObj.get("audioPath"));
                options = ((List<String>) jsonObj.get("options"));
                mnQuestion = new MNMCM(question, audioPath, options);
                break;
            case("mcs"):
                question = ((String) jsonObj.get("question"));
                audioPath = ((String) jsonObj.get("audioPath"));
                options = ((List<String>) jsonObj.get("options"));
                mnQuestion = new MNMCS(question, audioPath, options);
                break;
            case("smw"):
                audioPath = ((String) jsonObj.get("audioPath"));
                options = ((List<String>) jsonObj.get("options"));
                mnQuestion = new MNSMW(audioPath, options);
                break;
            case("sst"):
                audioPath = ((String) jsonObj.get("audioPath"));
                mnQuestion = new MNSST(audioPath);
                break;

            /**
             * writing
             */
            case("swt"):
                mnQuestion = new MNSWT();
                break;
            case("we"):
                mnQuestion = new MNWE();
                break;
        }

        // common
        mnQuestion.setQuestionId(questionId);
        mnQuestion.setTitle((String) jsonObj.get("title"));
        mnQuestion.setContent((String) jsonObj.get("content"));
        mnQuestion.setLessonPath((String) jsonObj.get("lessonPath"));


        jsonResponse.put("STATUS", "SUCCESS");
        jsonResponse.put("MSG", mnQuestion);
        return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
    }
}
