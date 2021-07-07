package com.sj.pte.modules.practice.bean;/**
 * Created by TUTEHUB on 2021-07-06 13:12.
 * Copyright © 2021 TUTEHUB. All rights reserved.
 * ------------------------
 * Non-disclosure Terms
 * -------------------------
 * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
 * Developer fengpeng agrees with above terms.
 * Technique Support: jobyme88.com
 */

import com.sj.pte.general.MNPost;
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
@Document(collection = "MNUserCollectRecord")
public class MNUserCollectRecord extends MNPost {

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
