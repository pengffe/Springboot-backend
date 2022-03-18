package com.pengffe.pte.utils.autoIncKey;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeqInfo {

    @Id
    private String id;// 主键

    @Field
    private String collName;// 集合名称

    @Field
    private Long seqId;// 序列值

}
