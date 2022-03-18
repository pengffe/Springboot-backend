package com.pengffe.pte.utils.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.pengffe.pte.modules.question.bean.*;
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
        Question mnQuestion = new Question();
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
                mnQuestion = new RA();
                break;
            case ("rs"):
                audioPath = ((String) jsonObj.get("audioPath"));
                mnQuestion = new RS(audioPath);
                break;
            case ("rl"):
                audioPath = ((String) jsonObj.get("audioPath"));
                imagePath = ((String) jsonObj.get("imagePath"));
                mnQuestion = new RL(audioPath, imagePath);
                break;
            case ("di"):
                imagePath = ((String) jsonObj.get("imagePath"));
                sampleAnswers = ((List<String>) jsonObj.get("sampleAnswers"));
                mnQuestion = new DI(imagePath, sampleAnswers);
                break;
            case ("asq"):
                audioPath = ((String) jsonObj.get("audioPath"));
                mnQuestion = new ASQ(audioPath);
                break;

            /**
             * reading
             */
            case ("rmcs"):
                question = ((String) jsonObj.get("question"));
                options = ((List<String>) jsonObj.get("options"));
                mnQuestion = new RMCS(question, options);
                break;
            case("rmcm"):
                question = ((String) jsonObj.get("question"));
                options = ((List<String>) jsonObj.get("options"));
                mnQuestion = new RMCM(question, options);
                break;
            case("ro"):
                mnQuestion = new RO();
                break;
            case("rfib"):
                options = ((List<String>) jsonObj.get("options"));
                mnQuestion = new RFIB(options);
                break;
            case("rwfib"):
                mnQuestion = new RWFIB();
                break;

            /**
             * listening
             */
            case("fib"):
                audioPath = ((String) jsonObj.get("audioPath"));
                mnQuestion = new FIB(audioPath);
                break;
            case("hcs"):
                audioPath = ((String) jsonObj.get("audioPath"));
                options = ((List<String>) jsonObj.get("options"));
                mnQuestion = new HCS(audioPath, options);
                break;
            case("hiw"):
                audioPath = ((String) jsonObj.get("audioPath"));
                mnQuestion = new HIW(audioPath);
                break;
            case("wfd"):
                audioPath = ((String) jsonObj.get("audioPath"));
                mnQuestion = new WFD(audioPath);
                break;
            case("mcm"):
                question = ((String) jsonObj.get("question"));
                audioPath = ((String) jsonObj.get("audioPath"));
                options = ((List<String>) jsonObj.get("options"));
                mnQuestion = new MCM(audioPath, question, options);
                break;
            case("mcs"):
                question = ((String) jsonObj.get("question"));
                audioPath = ((String) jsonObj.get("audioPath"));
                options = ((List<String>) jsonObj.get("options"));
                mnQuestion = new MCS(audioPath, question, options);
                break;
            case("smw"):
                audioPath = ((String) jsonObj.get("audioPath"));
                options = ((List<String>) jsonObj.get("options"));
                mnQuestion = new SMW(audioPath, options);
                break;
            case("sst"):
                audioPath = ((String) jsonObj.get("audioPath"));
                mnQuestion = new SST(audioPath);
                break;

            /**
             * writing
             */
            case("swt"):
                mnQuestion = new SWT();
                break;
            case("we"):
                mnQuestion = new WE();
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
