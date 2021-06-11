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


import com.sj.pte.modules.question.bean.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @description
 */
@Service
public class CheckServiceImpl implements CheckService {
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

    /**
     *
     * @param questionType "ra" or "ra-1"
     * @return
     */
    @Override
    public MNQuestion checkType(String questionType) {
        String type  = questionType;
        if (questionType.contains("-")){
            type = questionType.split("-")[0];
        }

        switch (type) {
            /**
             * speaking
             */
            case ("ra"):
                return new MNRA();
            case ("rs"):
                return new MNRS();
            case ("rl"):
                return new MNRL();
            case ("di"):
                return new MNDI();
            case ("asq"):
                return new MNASQ();

            /**
             * reading
             */
            case ("rmcs"):
                return new MNRMCS();
            case("rmcm"):
                return new MNRMCM();
            case("ro"):
                return new MNRO();
            case("rfib"):
                return new MNRFIB();
            case("rwfib"):
                return new MNRWFIB();

            /**
             * listening
             */
            case("fib"):
                return new MNFIB();
            case("hcs"):
                return new MNHCS();
            case("hiw"):
                return new MNHIW();
            case("mcm"):
                return new MNMCM();
            case("mcs"):
                return new MNMCS();
            case("smw"):
                return new MNSMW();
            case("sst"):
                return new MNSST();
            case("wfd"):
                return new MNWFD();

            /**
             * writing
             */
            case("swt"):
                return new MNSWT();
            case("we"):
                return new MNWE();
        }
        return new MNQuestion();
    }
}
