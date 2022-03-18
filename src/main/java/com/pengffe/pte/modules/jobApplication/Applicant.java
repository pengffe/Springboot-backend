package com.pengffe.pte.modules.jobApplication;

import com.pengffe.pte.utils.autoIncKey.AutoIncKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @descrption
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("Applicant")
public class Applicant {
    @Id
    @AutoIncKey
    private long id;

    private String firstName;
    private String lastName;

    private String email;
    private String phone;

    private Date birth;
    private String visa;
    private String CVPath;
}
