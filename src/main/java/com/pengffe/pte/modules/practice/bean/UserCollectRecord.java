package com.pengffe.pte.modules.practice.bean;

import com.pengffe.pte.general.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @descrption
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "UserCollectRecord")
public class UserCollectRecord extends Post {

    private String userId;
    //speaking
    private List<String> ra;
    private List<String> rs;
    private List<String> di;
    private List<String> rl;
    private List<String> asq;
    //listening
    private List<String> sst;
    private List<String> mcs;
    private List<String> mcm;
    private List<String> fib;
    private List<String> smw;
    private List<String> hiw;
    private List<String> hcs;
    private List<String> wfd;
    //reading
    private List<String> rmcs;
    private List<String> rmcm;
    private List<String> ro;
    private List<String> rfib;
    private List<String> rwfib;
    //writing
    private List<String> we;
    private List<String> swt;
}
