package com.gloryroad.demo.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class HttpUtil {
    public static String getToken(HttpServletRequest request) {
        String token = null;


        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            Cookie[] var = cookies;
            int var2 = cookies.length;
            for (int var3 = 0; var3 < var2; ++var3) {
                Cookie cookie = var[var3];
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                }
            }
            if (StringUtil.isBlank(token)) {
                token = request.getHeader("token");
                if (StringUtil.isBlank(token)) {
                    token = request.getParameter("token");
                }
            }
        }
        return token;
    }

}
