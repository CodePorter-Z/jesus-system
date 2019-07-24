package com.jesus.common.base.constant;

/**
 * GlobalConstant
 *
 * @author Jesus[zhoubing_ssr@163.com]
 * at      2019/7/24 15:46
 */
public interface GlobalConstant {

    interface Sys{
        Long REDIS_DEFAULT_EXPIRE = 30L*60;
    }

    interface User{
        //登录时存储到request中的key
        String LOGIN_ACCOUNT = "account";
    }
}
