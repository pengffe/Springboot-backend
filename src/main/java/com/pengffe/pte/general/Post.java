package com.pengffe.pte.general;

import com.pengffe.pte.utils.autoIncKey.AutoIncKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @description The most general bean
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("post")
public class Post {
    @Id
    @AutoIncKey
    private Long id;

    private String title;
    private String content;
    /**
     * @description When the post is published
     */
    private Date postDate = new Date();
    /**
     * @description When the post is lately modified
     */
    private Date modifiedDate;
    /**
     *
     */
    private String author;
    /**
     * @values Draft / Published / Open to Employees
     */
    private String status;





}
