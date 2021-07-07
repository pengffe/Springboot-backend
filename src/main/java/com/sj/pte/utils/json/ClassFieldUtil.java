package com.sj.pte.utils.json;/**
 * Created by TUTEHUB on 2021-07-06 12:45.
 * Copyright © 2021 TUTEHUB. All rights reserved.
 * ------------------------
 * Non-disclosure Terms
 * -------------------------
 * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
 * Developer fengpeng agrees with above terms.
 * Technique Support: jobyme88.com
 */

import java.lang.reflect.Method;

/**
 * @descrption
 */

public class ClassFieldUtil {

    public static Object getFieldValueByName(String fieldName, Object o) {
        String firstLetter = fieldName.substring(0, 1).toUpperCase();
        String getter = "get" + firstLetter + fieldName.substring(1);
        try {
            Method method = o.getClass().getMethod(getter);
            return method.invoke(o);
        } catch (Exception e) {
            getter = "is" + firstLetter + fieldName.substring(1);
            try {
                Method method = o.getClass().getMethod(getter);
                return method.invoke(o);
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return null;
    }
}
