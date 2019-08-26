package com.jesus.common.base.bind.annotation;


import com.jesus.common.base.constant.GlobalConstant;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginAccount {

    String value() default GlobalConstant.User.LOGIN_ACCOUNT;

}
