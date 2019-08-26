package com.code.proter.common.utils;

/**
 * ValidateUtil
 *
 * @author Jesus[zhoubing_ssr@163.com]
 * at      2019/8/2 15:16
 */
public class ValidateUtil {

    //手机号长度
    private static final int MOBILE_SIZE = 11;

    //密码最大长度
    private static final int PWD_MAX_LENGTH = 16;

    //密码最短长度
    private static final int PWD_MIN_LENGTH = 6;

    //手机号第一位字符
    private static final String MOBILE_FIRST_PLACE = "1";

    public static boolean isMobile(String mobile){
        if(CommonUtil.isEmpty(mobile)){
            return false;
        }
        if(!MOBILE_FIRST_PLACE.equals(mobile.substring(0,1))){
            return false;
        }
        if(mobile.length() != MOBILE_SIZE){
            return false;
        }
        return true;
    }

    public static boolean isPassLength(String password){
        if(CommonUtil.isEmpty(password)){
            return false;
        }
        int length = password.length();
        if(length > PWD_MAX_LENGTH || length < PWD_MIN_LENGTH){
            return false;
        }
        return true;
    }

}
