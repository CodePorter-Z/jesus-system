package com.jesus.user.shiro;

import com.jesus.common.utils.CommonUtil;
import com.jesus.user.modules.user.service.IUserService;
import com.jesus.user.shiro.serializable.ByteSourceUtils;
import com.jesus.user.domain.user.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.Set;

@Slf4j
public class ShiroRealm extends AuthorizingRealm {

    @Resource
    private IUserService userService;

    //权限信息，包括角色以及权限
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.debug("ShiroRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        //如果身份认证的时候没有传入User对象，这里只能取到userName
        //也就是SimpleAuthenticationInfo构造的时候第一个参数传递需要User对象
        User user  = (User)principals.getPrimaryPrincipal();

        Set<String> permissions =  userService.getPermissionsByUserName(user.getUsername());
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
    }

    /**
     *
     * 主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。
     *
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        log.debug("ShiroRealm.doGetAuthenticationInfo()");
        //获取用户的输入的账号.
        String userName = (String)token.getPrincipal();

        if(CommonUtil.isEmpty(userName)){
            throw new UnknownAccountException("the username is empty. chance is user not input username so check username");
        }

        //通过username从数据库中查找 User对象.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        User user = userService.findByUserName(userName);
        if(CommonUtil.isNull(user)){
            throw new UnknownAccountException("account not exist");
        }

        if(user.getState() != User.State.ENABLED){
            throw new LockedAccountException("account has locked. contact system manager unlock");
        }

        /**
         * @parmater 1
         *     这里传入的是user对象，比对的是用户名，直接传入用户名也没错，但是在授权部分就需要自己重新从数据库里取权限
         *       形参2
         *     密码
         *       形参3
         *     盐值
         *       形参4
         *     自定义realm name
         *
         *  当使用redis时，形参1 比如传入对象，因为Redis需要id 作为key 否则报异常
         *  class java.lang.String must has getter for field: authCacheKey or id
         */
        return new SimpleAuthenticationInfo(user, user.getPassword(), ByteSourceUtils.bytes(user.getSalt()), getName());
    }

}