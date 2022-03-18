package com.pengffe.pte.utils.json;

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
