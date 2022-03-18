package com.pengffe.pte.modules.comment;

import com.pengffe.pte.utils.autoIncKey.AutoIncKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("Comment")
public class Comment {
    @Id
    @AutoIncKey
    private Long id;
    String questionId;
    String userId;
    String message;
}
