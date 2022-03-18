package com.pengffe.pte.modules.question.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "rmcm")
public class RMCM extends Question {
    private String question;
    /**
     * @decription Correct answer will be wrapped with customized tag
     * <answer></answer>
     */
    private List<String> options;
}
