//package cn.edu.nju.eczuul.filter;
//
//
//import cn.edu.nju.eczuul.common.Const;
//import cn.edu.nju.eczuul.redis.UserKey;
//import cn.edu.nju.eczuul.service.RedisUtil;
//import cn.edu.nju.eczuul.util.CookieUtil;
//import entity.user.User;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//
///**
// * 重新设置用户session在redis的有效期
// */
//@Component
//public class SessionExpireFilter implements Filter {
//    @Autowired
//    RedisUtil redisUtil;
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
//
//        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
//
//        if(StringUtils.isNotEmpty(loginToken)){
//            //判断logintoken是否为空或者""；
//            //如果不为空的话，符合条件，继续拿user信息
//            User user = (User)redisUtil.get(UserKey.getByName,loginToken);
//            if(user != null){
//                //如果user不为空，则重置session的时间，即调用expire命令
//                redisUtil.expire(UserKey.getByName.getPrefix() + loginToken, Const.RedisCacheExpireTime.REDIS_SESSION_EXTIME);
//            }
//        }
//        filterChain.doFilter(servletRequest,servletResponse);
//    }
//
//
//    @Override
//    public void destroy() {
//
//    }
//}
