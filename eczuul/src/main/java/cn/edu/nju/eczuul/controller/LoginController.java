package cn.edu.nju.eczuul.controller;

import cn.edu.nju.eczuul.redis.UserKey;
import cn.edu.nju.eczuul.service.RedisUtil;
import cn.edu.nju.eczuul.common.Const;
import cn.edu.nju.eczuul.controller.param.LoginParam;
import cn.edu.nju.eczuul.feign.UserFeignClient;

import cn.edu.nju.eczuul.response.Response;
import cn.edu.nju.eczuul.util.CookieUtil;
import entity.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import rest.CodeMsg;
import rest.RestResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class LoginController {
    private UserFeignClient userFeignClient;
    private RedisUtil redisUtil;

    public LoginController(UserFeignClient userFeignClient, RedisUtil redisUtil) {
        this.userFeignClient = userFeignClient;
        this.redisUtil = redisUtil;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Response<User> login(HttpServletResponse response,
                                HttpSession session,
                                @Valid LoginParam loginParam) {
        User user = User.builder().mobile(loginParam.getMobile()).password(loginParam.getPassword()).build();
        RestResponse<User> result = userFeignClient.signIn(user);
        if (result.isSuccess()) {
            CookieUtil.writeWithCookie(response, session.getId());
            user.setPassword(null);
            redisUtil.set(UserKey.getByName, session.getId(), result.getData(), Const.RedisCacheExpireTime.REDIS_SESSION_EXTIME);
            return Response.success(user);
        }
        return Response.error(CodeMsg.USER_INFO_ERROR);
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    @ResponseBody
    public Response<User> signUp(HttpServletResponse response,
                                HttpSession session,
                                @Valid LoginParam loginParam) {
        User user = User.builder().mobile(loginParam.getMobile()).password(loginParam.getPassword()).build();
        RestResponse<User> result = userFeignClient.signUp(user);
        if (result.isSuccess()) {
            CookieUtil.writeWithCookie(response, session.getId());
            user.setPassword(null);
            redisUtil.set(UserKey.getByName, session.getId(), result.getData(), Const.RedisCacheExpireTime.REDIS_SESSION_EXTIME);
            return Response.success(user);
        }
        return Response.error(CodeMsg.USER_INFO_ERROR);
    }

    /**
     * @return login page
     */
    @RequestMapping(value = "/logout")
    public String doLogout(HttpServletRequest request, HttpServletResponse response) {
        String token = CookieUtil.readLoginToken(request);
        CookieUtil.delLoginToken(request, response);
        redisUtil.del(UserKey.getByName, token);
        return "login";
    }
}
