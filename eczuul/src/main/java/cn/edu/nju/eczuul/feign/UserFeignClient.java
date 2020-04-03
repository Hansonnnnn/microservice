package cn.edu.nju.eczuul.feign;

import entity.user.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import rest.RestResponse;

@FeignClient(value = "route")
public interface UserFeignClient {
    @PostMapping(value = "/route-user/users/signIn")
    RestResponse<User> signIn(@RequestBody User user);
}
