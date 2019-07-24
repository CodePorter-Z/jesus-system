package com.jesus.user.shiro.data;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Login
 *
 * @author Archx[archx@foxmail.com]
 * at 2018/7/13 22:27
 */
@Data
public class Login {

    private String username;
    private String password;
    private boolean rememberMe = false;
}
