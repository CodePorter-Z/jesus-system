package com.code.proter.user.shiro.filter;

import com.code.proter.common.base.constant.GlobalConstant;
import com.code.proter.user.domain.user.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Optional;

@Slf4j
public class LoginAccountFilter extends PathMatchingFilter {

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {
        log.debug("LoginAccountFilter.onPreHandle()");

        Subject subject = SecurityUtils.getSubject();

        //获取user信息
        User user = (User) subject.getPrincipal();

        Optional.ofNullable(user).ifPresent(u -> request.setAttribute(GlobalConstant.User.LOGIN_ACCOUNT, u));
        return true;
    }

}


