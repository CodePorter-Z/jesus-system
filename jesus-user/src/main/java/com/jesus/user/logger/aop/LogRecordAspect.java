package com.jesus.user.logger.aop;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 切面 打印请求、返回参数信息
 */
@Aspect // 定义一个切面
@Configuration
@Slf4j
public class LogRecordAspect {

    // 定义切点Pointcut
    @Pointcut("execution(* com.jesus.user.web.*Controller.*(..))")
    public void executionService() {
    }

    @Around("executionService()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();

        String method = request.getMethod();
        String uri = request.getRequestURI();
        String paraString = JSON.toJSONString(request.getParameterMap());
        log.info("***************************************************");
        log.info("请求开始, URI: {}, method: {}, params: {}", uri, method, paraString);

        long start = System.currentTimeMillis();
        // result的值就是被拦截方法的返回值
        Object result = pjp.proceed();
        long elapsed = System.currentTimeMillis() - start;
        log.info("请求结束，return :{}, elapsed:{}  ", JSON.toJSONString(result),elapsed);
        return result;
    }
}