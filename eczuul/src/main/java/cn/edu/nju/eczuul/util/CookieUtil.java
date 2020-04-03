package cn.edu.nju.eczuul.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class CookieUtil {
    private static final String COOKIE_DOMAIN = "localhost";
    private static final String COOKIE_NAME = "seckill_login_toke";

    public static String readLoginToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie ck : cookies) {
                log.info("read cookieName: {}, cookieValue: {}", ck.getName(), ck.getValue());
                if (StringUtils.equals(COOKIE_NAME, ck.getName())) {
                    log.info("return cookieName: {}, cookieValue: {}", ck.getName(), ck.getValue());
                    return ck.getValue();
                }
            }
        }
        return null;
    }

    public static void writeWithCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie(COOKIE_NAME, token);
        cookie.setDomain(COOKIE_DOMAIN);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        //单位是秒。
        //如果这个maxage不设置的话，cookie就不会写入硬盘，而是写在内存。只在当前页面有效。
        cookie.setMaxAge(60 * 60 * 24 * 365); //如果是-1，代表永久
        log.info("write cookieName: {}, cookieValue: {}",cookie.getName(),cookie.getValue());
        response.addCookie(cookie);
    }

    public static void delLoginToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (Cookie ck : cookies) {
            if (StringUtils.equals(COOKIE_NAME, ck.getName())) {
                ck.setDomain(COOKIE_DOMAIN);
                ck.setPath("/");
                ck.setMaxAge(0);
                response.addCookie(ck);
                return;
            }
        }
    }
}
