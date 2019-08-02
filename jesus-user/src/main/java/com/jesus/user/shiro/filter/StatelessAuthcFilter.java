package com.jesus.user.shiro.filter;

import com.jesus.common.base.constant.GlobalConstant;
import com.jesus.common.response.Response;
import com.jesus.common.utils.CommonUtil;
import com.jesus.common.utils.CookieUtil;
import com.jesus.common.utils.RenderUtil;
import com.jesus.common.utils.encrypt.aes.AESUtil;
import com.jesus.user.domain.user.User;
import com.jesus.user.shiro.data.Login;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ConcurrentAccessException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class StatelessAuthcFilter extends AccessControlFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object o) throws Exception {
        log.debug("StatelessAuthcFilter.isAccessAllowed()");
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        log.debug("StatelessAuthcFilter.onAccessDenied()");

        HttpServletRequest request = WebUtils.toHttp(servletRequest);
        HttpServletResponse response = WebUtils.toHttp(servletResponse);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type,x-requested-with,X-Token");
        response.setHeader("Access-Control-Expose-Headers", "X-Token,timestamp");
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            response.sendError(404);
            return false;
        }
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.sendError(202);
            return false;
        }
        String url = request.getServletPath();
        String method = request.getMethod();
        log.info("请求方式:{} , 请求地址:{}", method, url);
        try {
            if (isLoginRequest(request, response)) {
                return true;
            } else if (url.contains("/logout")) {
                return true;
            } else {
                Subject subject = getSubject(request, response);
                if (CommonUtil.isNull(subject.getPrincipal())) {
                    throw new AuthenticationException();
                }else{
                    String token = request.getHeader("X-Token");
                    log.info("token:{}",token);
                }
            }
        } catch (AuthenticationException e) {
            RenderUtil.renderJson(response, Response.info("40013", "account info has expired", "验证信息已过期，请重新登录"));
            return false;
        } catch (Exception e) {
            log.error("请求：{}\t异常:{}", url, e.getMessage());
            RenderUtil.renderJson(response, Response.info("40000", "service Exception", "出错啦，请稍后尝试"));
            return false;
        }
        return true;
    }
}
