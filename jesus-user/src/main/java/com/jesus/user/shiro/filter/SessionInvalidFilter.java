package com.jesus.user.shiro.filter;

import com.jesus.common.response.Response;
import com.jesus.common.utils.RenderUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class SessionInvalidFilter extends AccessControlFilter {


    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object o) throws Exception {

        log.debug("SessionInvalidFilter.isAccessAllowed()");
        if (isLoginRequest(request, response)) {
            return true;
        } else {
            Subject subject = getSubject(request, response);
            // If principal is not null, then the user is known and should be allowed access.
            return subject.getPrincipal() != null;
        }
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

        log.debug("SessionInvalidFilter.onAccessDenied()");
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);

        //前端http请求中code为403的时候跳转到登陆页，R.fail()为你返回给前端的json对象
        RenderUtil.renderJson(httpServletResponse, Response.fail("验证信息已过期，请重新登录"));
        return false;
    }
}
