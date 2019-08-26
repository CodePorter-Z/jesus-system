package com.code.proter.common.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CookieUtil {

    public static void writeCookie(HttpServletResponse response, String key, String value) {
        writeCookie(response, key, value, 1800000, "/");
    }

    public static void writeCookie(HttpServletResponse response, String key, String value, int maxAge, String path) {

        try {

            Cookie cookie = new Cookie(key, value);
            cookie.setMaxAge(maxAge);
            cookie.setPath(path);
            response.addCookie(cookie);

        } catch (Exception e) {
            log.error("writeCookie 异常:{}",e.getMessage());
            e.printStackTrace();
        }
    }


    public static String getCookie(HttpServletRequest request, String key) {

        Cookie[] cookies = request.getCookies();
        if (CommonUtil.isNull(cookies)) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(key)) {
                return cookie.getValue();
            }
        }
        return null;

    }

}