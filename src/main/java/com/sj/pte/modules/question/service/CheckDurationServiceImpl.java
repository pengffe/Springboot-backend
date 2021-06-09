package com.sj.pte.modules.question.service;/**
 * Created by TUTEHUB on 2021/6/4 12:48 PM.
 * Copyright © 2021 TUTEHUB. All rights reserved.
 * ------------------------
 * Non-disclosure Terms
 * -------------------------
 * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
 * Developer dph agrees with above terms.
 * Technique Support: jobyme88.com
 * Contact: noreply@fengcaoculture.com
 */


import com.sj.pte.modules.question.bean.MNQuestion;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @description
 */
@Service
public class CheckDurationServiceImpl implements CheckDurationService {
    /**
     * triggered in findAll()
     * check updated and add attributes
     * if surpass 7 days, then change them
     */
    @Override
    public <T extends MNQuestion> List<T> checkDuration(List<T> tList) {
        for (T item: tList) {
            /* modify add */
            long insertPeriod = 0;
            if (item.getPostDate() != null){
                insertPeriod =  (new Date().getTime() - item.getPostDate().getTime())/(3600000 * 24);
                if (insertPeriod > 7) {
                    item.setAdd(false);
                }
                System.out.println("距初次创建时的时间：" + insertPeriod);
            }
            else {
                System.out.println("Post Date is null");
            }

            /* modify updated */
            long updatePeriod = 0;
            if(item.getModifiedDate() != null){
                updatePeriod =  (new Date().getTime() - item.getModifiedDate().getTime())/(3600000 * 24);
                if (updatePeriod > 7){
                    item.setUpdated(false);
                }
            }
            else {
                System.out.println("Update time not set");
            }
        }
        return tList;
    }
}
