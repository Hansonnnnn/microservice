package cn.edu.nju.user.rest;

import cn.edu.nju.user.dao.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.user.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rest.RestResponse;

@Api(tags = "用户服务", description = "提供用户服务相关Rest API")
@RestController("/users")
public class UserController {
    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @ApiOperation("用户注册接口")
    @PostMapping("/signUp")
    public User signUp(@RequestBody User user) {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        String pwd = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        newUser.setPassword(pwd);
        newUser = userRepository.save(newUser);
        newUser.setPassword("");
        return newUser;
    }

    @ApiOperation("用户登录验证接口")
    @PostMapping("/signIn")
    public boolean signIn(@RequestBody User user) {
        User foundUser = userRepository.findByUsername(user.getUsername());
        if (foundUser == null) return false;
        return BCrypt.checkpw(user.getPassword(), foundUser.getPassword());
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