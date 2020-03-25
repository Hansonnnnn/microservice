package cn.edu.nju.eczuul.controller;

import cn.edu.nju.eczuul.feign.UserFeignClient;
import cn.edu.nju.eczuul.response.CodeMsg;
import cn.edu.nju.eczuul.response.Response;
import entity.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class LoginController {
    private UserFeignClient userFeignClient;

    public LoginController(UserFeignClient userFeignClient) {
        this.userFeignClient = userFeignClient;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Response<User> login(@RequestParam("mobile")String mobile,
                                @RequestParam("password")String password) {
        User user = User.builder().mobile(mobile).password(password).build();
        boolean result = userFeignClient.signIn(user);
        if (result) {
            return Response.success(user);
        }
        return Response.error(CodeMsg.USER_INFO_ERROR);
    }

    /**
     * todo session and redis
     * @return login page
     */
    @RequestMapping(value = "/logout")
    public String doLogout() {
        return "login";
    }
}
