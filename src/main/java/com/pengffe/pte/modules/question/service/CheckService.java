package com.pengffe.pte.modules.question.service;

import com.pengffe.pte.modules.question.bean.Question;

import java.util.List;

/**
 * @description
 */
public interface CheckService {

    <T extends Question> List<T> checkDuration(List<T> tClass);

    Question checkType (String questionType);
}
