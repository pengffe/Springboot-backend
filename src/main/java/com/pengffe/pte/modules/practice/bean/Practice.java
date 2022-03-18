package com.pengffe.pte.modules.practice.bean;

import com.pengffe.pte.general.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Practice")
public class Practice extends Post {

    private String questionId;
    private String answer;//address or text

    private String userId;

//    public Practice(String questionId, String answer, String username) {
//        this.questionId = questionId;
//        this.answer = answer;
//        this.username = username;
//    }

}
