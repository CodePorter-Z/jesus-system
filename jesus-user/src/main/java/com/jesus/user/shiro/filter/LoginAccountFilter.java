package com.jesus.user.shiro.filter;

import com.jesus.common.base.constant.GlobalConstant;
import com.jesus.common.base.redis.service.RedisService;
import com.jesus.common.utils.CommonUtil;
import com.jesus.common.utils.DateUtil;
import com.jesus.common.utils.IPUtil;
import com.jesus.user.logger.service.LogActionService;
import com.jesus.user.domain.logaction.LogAction;
import com.jesus.user.domain.user.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Slf4j
public class LoginAccountFilter extends PathMatchingFilter {

//    @Resource
//    private LogActionService logActionService;
//
//    @Resource
//    private RedisService redisService;

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {

        log.debug("LoginAccountFilter.onPreHandle()");
//        HttpServletRequest req = (HttpServletRequest) request;
        Subject subject = SecurityUtils.getSubject();

        //获取user信息
        User user = (User) subject.getPrincipal();
//
//        if(CommonUtil.isNull(user)){
//            //防止登录与退出空指针
//            user = (User)redisService.get(GlobalConstant.Redis.REDIS_USER);
//            if(CommonUtil.isNull(user)){
//                return true;
//            }
//        }
//
//        //请求日志
//        LogAction logAction = new LogAction();
//        //请求状态
//        int type = 1;
//        if(CommonUtil.isNull(user)){
//            type = -1;
//        }else if(user.getState() != User.State.ENABLED){
//            type = -1;
//        }

        //存储user信息到request作用域
        Optional.ofNullable(user).ifPresent(u -> request.setAttribute(GlobalConstant.User.LOGIN_ACCOUNT, u));
        try {
//
//            //TODO  登出空指针
//            logAction.setUsername(user.getUsername());
//            //获取IP地址
//            logAction.setIp(IPUtil.getIpAddr(req));
//            logAction.setType(type);
//            //请求路径
//            logAction.setMethod(req.getRequestURI());
//            logAction.setCreateTime(DateUtil.currentTime());
//            logActionService.save(logAction);
//
//            //清除缓存
//            if(logAction.getMethod().contains("/logout")){
//                redisService.remove(GlobalConstant.Redis.REDIS_USER);
//            }
        } catch (Exception e) {
            log.error("LoginAccountFilter.onPreHandle() Exception : {}",e.getMessage());
        }
        return true;
    }

}


