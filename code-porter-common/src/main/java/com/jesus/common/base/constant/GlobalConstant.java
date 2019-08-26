package com.jesus.common.base.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * GlobalConstant
 *
 * @author zhoubing
 * at      2019/7/24 15:46
 */
public interface GlobalConstant {

    /**
     * 系统常量池
     */
    interface Sys{
        Long REDIS_DEFAULT_EXPIRE = 30L*60;
    }

    /**
     * 用户常量池
     */
    interface User{
        //登录时存储到request中的key
        String LOGIN_ACCOUNT = "account";
        String ID = "userId";
    }

    /**
     * Redis 常量池
     */
    interface Redis{
        String REDIS_USER = "redis_user";
    }

    /**
     * 渠道免密设置
     */
    class Channel{
        public static Map<String,String> resourceMap = new HashMap<>();

        static {
            //系统管理员
            resourceMap.put("18888888888","jAdmin");
        }
    }
}
