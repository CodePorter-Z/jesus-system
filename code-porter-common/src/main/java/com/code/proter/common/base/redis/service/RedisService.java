package com.code.proter.common.base.redis.service;

/**
 * RedisService
 *
 * @author Jesus[zhoubing_ssr@163.com]
 * at      2019/7/24 15:43
 */
public interface RedisService {

    /**
     * 获取指定key
     * @param key
     * @return
     */
    Object get(String key);

    /**
     * 删除指定key
     * @param key
     */
    void remove(String key);

    /**
     * 存储到redis
     * @param key
     * @param value
     */
    void set(String key, Object value);

    /**
     * 存储到redis 并设置过期时间
     * @param key
     * @param value
     * @param timeout
     */
    void set(String key, String value, long timeout);

    /**
     * 判断key是否存在
     * @param key 键
     * @return true 存在 false不存在
     */
    Boolean hasKey(String key);




}
