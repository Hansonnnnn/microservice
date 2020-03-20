package cn.edu.nju.eczuul.feign;

import entity.user.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("user")
public interface UserFeignClient {
    @RequestMapping("/users/signIn")
    boolean login(@RequestBody User user);
}
