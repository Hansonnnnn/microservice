package cn.edu.nju.eczuul.feign;

import entity.user.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user")
public interface UserFeignClient {
    @PostMapping(value = "/users/signIn")
    boolean signIn(@RequestBody User user);
}
