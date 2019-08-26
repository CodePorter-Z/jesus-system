package com.code.proter.common.base.redis.service.impl;

import com.code.proter.common.base.constant.GlobalConstant;
import com.google.common.base.Preconditions;
import com.code.proter.common.base.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;


/**
 * IRedisService
 *
 * @author Jesus[zhoubing_ssr@163.com]
 * at      2019/7/24 15:44
 */
@Service
@Slf4j
public class IRedisService implements RedisService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Object get(String key) {
        Object value = null;
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        if (redisTemplate.hasKey(key)) {
            value = ops.get(key);
        }
        log.info("getKey. [OK] key={}, value={}", key, value);
        return value;
    }

    @Override
    public void remove(String key) {
        redisTemplate.delete(key);
        log.info("deleteKey. [OK] key={}", key);

    }

    @Override
    public void set(String key, Object value) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(key), "Redis key is not null");

        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        ops.set(key, value);
        //TimeUnit.MINUTES  时间单位设置 这里是秒
        redisTemplate.expire(key, GlobalConstant.Sys.REDIS_DEFAULT_EXPIRE, TimeUnit.SECONDS);
        log.info("setKey. [OK] key={}, value={}, expire=默认超时时间", key, value);


    }

    @Override
    public void set(String key, String value, long timeout) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(key), "Redis key is not null");
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        ops.set(key, value);
        redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
        log.info("setKey. [OK] key={}, value={}, timeout={}", key, value, timeout);
    }

    @Override
    public Boolean hasKey(String key){
        Preconditions.checkArgument(StringUtils.isNotEmpty(key), "Redis key is not null");
        log.info("hasKey. [OK] key={}", key);
        return redisTemplate.hasKey(key);
    }

}
