package com.code.proter.common.base.redis.model;

import lombok.Data;

@Data
public class RedisOptions {

    private String host = "127.0.0.1";
    private int    port = 6379;
    private String password;
    private int timeout = 2000;

}
