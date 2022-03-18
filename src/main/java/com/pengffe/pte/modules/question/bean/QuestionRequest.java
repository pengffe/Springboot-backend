package com.pengffe.pte.modules.question.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @descrption
 */

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRequest extends Question {
    private Date testDate;
    private String audioPath;
    private String imagePath;
    private List<String>  sampleAnswers;
    private String question;
    private List<String> options;
}
