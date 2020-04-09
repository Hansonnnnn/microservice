package cn.edu.nju.user.rest;

import cn.edu.nju.user.dao.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.user.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rest.CodeMsg;
import rest.RestResponse;

import java.util.HashSet;
import java.util.Set;

@Api(tags = "用户服务", description = "提供用户服务相关Rest API")
@RestController("UserController")
@RequestMapping("/users")
public class UserController {
    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @ApiOperation("用户注册接口")
    @PostMapping("/signUp")
    public RestResponse<User> signUp(@RequestBody User user) {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        String pwd = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        newUser.setMobile(user.getMobile());
        newUser.setPassword(pwd);
        Set<String> addresses = user.getAddresses();
        if (addresses == null) {
            addresses = new HashSet<>();
            addresses.add("上海市-市辖区-普陀区-曹杨新村街道");
            newUser.setAddresses(addresses);
        } else {
            newUser.setAddresses(addresses);
        }
        newUser = userRepository.save(newUser);
        newUser.setPassword("");
        return RestResponse.success(newUser);
    }

    @ApiOperation("用户登录验证接口")
    @PostMapping("/signIn")
    public RestResponse<User> signIn(@RequestBody User user) {
        User foundUser = userRepository.findByMobile(user.getMobile());
        if (foundUser == null) return RestResponse.error(HttpStatus.NOT_FOUND, CodeMsg.USER_NOT_FOUND);
        if (BCrypt.checkpw(user.getPassword(), foundUser.getPassword())) {
            user.setPassword("");
            foundUser.setPassword("");
            return RestResponse.success(foundUser);
        } else {
            return RestResponse.error(HttpStatus.NOT_FOUND, CodeMsg.USER_INFO_ERROR);
        }
    }

    /**
     * todo 登录之后就不用再带着密码来修改地址，如果没有登录就需要跳转到登录页
     * @param user
     * @return
     */
    @ApiOperation("修改收货地址")
    @PostMapping("/addresses")
    public RestResponse addAddress(@RequestBody User user) {
        RestResponse restResponse = new RestResponse();
        User foundUser = userRepository.findByUsername(user.getUsername());
        if (user.getPassword().equals("") || BCrypt.checkpw(user.getPassword(), foundUser.getPassword())) {
            restResponse.setStatus(400);
            restResponse.setCode(40000);
            restResponse.setMessage("用户名或密码错误");
            return restResponse;
        }
        foundUser.getAddresses().addAll(user.getAddresses());
        userRepository.save(foundUser);
        foundUser.setPassword("");
        restResponse.setStatus(200);
        restResponse.setMessage(writeValueAsString(foundUser));
        return restResponse;
    }

    private String writeValueAsString(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return e.getMessage();
        }

    }
}
