package com.jesus.user.web.handler;

import com.jesus.common.response.Response;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

/**
 *
 * 异常处理控制类
 *
 */
@ControllerAdvice(annotations = RestController.class)
public class DefaultExceptionHandler {

    /**
     *  该注解 @ExceptionHandler(异常类型) 捕获指定异常
     *      形参为指定异常 此处捕获 UnauthorizedException
     *
     *  可自定义返回浏览器状态 使用 @ResponseStatus(状态码)
     *      返回码在 HttpStatus 中指定
     * @param request
     * @param handler
     * @param ex
     * @return
     */
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public Response handleUnauthorizedException(NativeWebRequest request, Object handler, UnauthorizedException ex) {
        return Response.error(ex.getMessage());
    }
}
