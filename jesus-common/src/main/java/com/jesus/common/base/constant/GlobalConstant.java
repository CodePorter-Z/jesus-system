package com.jesus.common.base.constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * GlobalConstant
 *
 * @author Jesus[zhoubing_ssr@163.com]
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
        String USER_ID = "userId";
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
        public static List<String> channelList = new ArrayList<>();

        static {
            //系统管理员
            channelList.add("jAdmin");
        }
    }
}
