package com.jesus.user.shiro.data;

import com.jesus.user.shiro.token.CipherFreeToken;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

public class CipherFreeHashedCredentialsMatcher extends HashedCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

        if (token instanceof CipherFreeToken) { // 免密码登录
            return true;
        }
        return super.doCredentialsMatch(token, info);
    }
}
