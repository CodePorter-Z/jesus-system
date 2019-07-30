package com.jesus.user.shiro.filter;

import com.jesus.common.base.constant.GlobalConstant;
import com.jesus.common.base.redis.service.RedisService;
import com.jesus.common.response.Response;
import com.jesus.common.utils.CommonUtil;
import com.jesus.common.utils.RenderUtil;
import com.jesus.common.utils.encrypt.aes.AESUtil;
import com.jesus.user.domain.user.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class StatelessAuthcFilter extends AccessControlFilter {

//    @Resource
//    private RedisService redisService;

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
        log.info(url);
        try {
//            if(url.contains("/login")){
//                Subject subject = this.getSubject(request, response);
//                User userInfo = (User) subject.getPrincipal();
//                if (!CommonUtil.isNull(userInfo)) {
//                    String userId = String.valueOf(userInfo.getId());
//
//                    //id 丢失处理
//                    if (CommonUtil.isEmpty(userId)) {
//                        User user = (User) redisService.get(GlobalConstant.Redis.REDIS_USER);
//                        if (!CommonUtil.isNull(user)) {
//                            if (user.getUsername().equals(userInfo.getUsername())) {
//                                userInfo.setId(user.getId());
//                                userId = String.valueOf(user.getId());
//                            }
//                        }
//                    }
//                    String encrypt_token = AESUtil.Encrypt(userId);
//                    if (url.contains("/doLogin")) {
//                        //设置请求头信息
//                        response.setHeader("X-Token", encrypt_token);
//                        response.setHeader("timestamp", String.valueOf(System.currentTimeMillis()));
//                        response.setHeader("Auth-Type", userInfo.getRole().getRoleType());
//                    }
//                }
//            }else{
//                //获取请求头信息
//                String token = request.getHeader("X-Token");
//                String timestamp = request.getHeader("timestamp");
//                if(CommonUtil.isEmpty(token) || CommonUtil.isEmpty(timestamp)){
//                    throw new AuthenticationException();
//                }
//            }
        } catch (AuthenticationException e) {
            RenderUtil.renderJson(response, Response.info("40013","account info has expired","验证信息已过期，请重新登录"));
            return false;
        }catch (Exception e){
            log.error("请求：{}\t异常:{}",url,e.getMessage());
            RenderUtil.renderJson(response, Response.info("40000","service Exception","出错啦，请稍后尝试"));
            return false;
        }
        return true;
    }
}
