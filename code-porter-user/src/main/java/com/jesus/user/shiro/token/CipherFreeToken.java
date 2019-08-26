package com.jesus.user.shiro.token;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * CipherFreeToken
 *
 * @author Archx[archx@foxmail.com]
 * at 2018/7/13 21:40
 */
public class CipherFreeToken extends UsernamePasswordToken {

    private static final long serialVersionUID = 5478435999847981116L;

    public CipherFreeToken() {
    }

    public CipherFreeToken(String username) {
        super(username, "");
    }

    public CipherFreeToken(String username, String host) {
        super(username, "", host);
    }

    public CipherFreeToken(String username, boolean rememberMe) {
        super(username, "", rememberMe);
    }

    public CipherFreeToken(String username, boolean rememberMe, String host) {
        super(username, "", rememberMe, host);
    }

    public CipherFreeToken(String username, String password, boolean rememberMe, String host) {
        super(username, password, rememberMe, host);
    }
}
