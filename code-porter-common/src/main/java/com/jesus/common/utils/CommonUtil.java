package com.jesus.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Collection;

public class CommonUtil {

    /**
     * 用于判断对象，集合
     * @param object
     * @return
     */
    public static boolean isNull(Object object){
        if(object == null || "".equals(object)){
            return true;
        }else if(object instanceof Collection){
           if(((Collection) object).size() == 0){
                return true;
            }
        }
        return false;
    }

    public static boolean isNotNull(Object obj){
        return !isNull(obj);
    }

    /**
     * 用于判断字符串
     *   采用apache lang3 工具包
     *   会判断value是否包含空格
     *      没有文本只有空格或者制表符等会返回true -> 否则false
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        return StringUtils.isBlank(str);
    }

    /**
     * （反射机制）判断对象所有属性是否有值
     *              如一个为Null 则返回false
     * @param object
     * @return
     * @throws Exception
     */
    public static boolean isObjectToNull(Object object) throws Exception{
        if (isNull(object)) {
            return true;
        }
        try {
            for (Field f : object.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                if (f.get(object) != null && StringUtils.isNotBlank(f.get(object).toString())) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
