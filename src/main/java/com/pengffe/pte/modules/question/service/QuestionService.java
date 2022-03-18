package com.pengffe.pte.modules.question.service;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.pengffe.pte.modules.question.bean.Question;
import com.pengffe.pte.modules.question.bean.QuestionRequest;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @description
 */
public interface QuestionService {

    /***********
     * Create
     ***********/
    <T extends Question> T save(T t);

    Question readRequestJSONToObject(String type, QuestionRequest mnQuestionRequest);

    /***********
     * Read
     ***********/
    <T> T findById(Class<T> tCLass, String questionId);

    <T> List<T> findAll(Class<T> tClass);

    <T> Page<T> findAllByPage(Class<T> tClass, int pageNum, int pageSize, String sortType);

    <T> Long findCount(Class<T> tClass);

    /***********
     * Update
     ***********/
    UpdateResult update(Question mnQuestion);

    <T> UpdateResult updateById(Class<T> tClass, String id, QuestionRequest mnQuestionRequest);

    <T> DeleteResult deleteById(Class<T> tCLass, String id);
}
