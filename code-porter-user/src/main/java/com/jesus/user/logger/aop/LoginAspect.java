package com.jesus.user.logger.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jesus.common.response.Response;
import com.jesus.common.utils.CommonUtil;
import com.jesus.common.utils.IPUtil;
import com.jesus.user.domain.logaction.LogAction;
import com.jesus.user.logger.service.ILogActionService;
import com.jesus.user.shiro.data.Login;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Configuration
@Slf4j
public class LoginAspect {

    @Autowired
    private ILogActionService logActionService;

    @Pointcut("@annotation(com.jesus.user.logger.annotation.Authorization)")
    public void LoginAspectLog() {

    }

    @Around("LoginAspectLog()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        //获取请求参数
        Map<String, Object> param = getFieldsName(pjp);

        String username = "";
        Object login = param.get("login");
        if(login instanceof Login){
            username = ((Login) login).getUsername();
        }

        //执行目标方法后
        Object result = pjp.proceed();

        LogAction logAction = new LogAction();
        logAction.setUsername(username);
        logAction.setCreateTime(new Date());
        logAction.setIp(IPUtil.getIpAddr(request));
        logAction.setType(-1);
        logAction.setMethod(request.getMethod());
        logAction.setUri(request.getRequestURI());

        JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(result));
        //执行成功后 记录日志
        if (json.containsKey("code")) {
            String code = json.getString("code");
            if ("20000".equals(code)) {
                logAction.setType(1);
            }
        }
        logActionService.save(logAction);
        return result;
    }

    /**
     * 获取参数列表
     *
     * @param joinPoint
     * @return
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     */
    private static Map<String, Object> getFieldsName(ProceedingJoinPoint joinPoint) {
        // 参数值
        Object[] args = joinPoint.getArgs();
        ParameterNameDiscoverer pnd = new DefaultParameterNameDiscoverer();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String[] parameterNames = pnd.getParameterNames(method);
        Map<String, Object> paramMap = new HashMap<>(32);
        for (int i = 0; i < parameterNames.length; i++) {
            paramMap.put(parameterNames[i], args[i]);
        }
        return paramMap;
    }
}
