package cn.edu.nju.eczuul.controller.param;

import cn.edu.nju.eczuul.validator.IsMobile;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class LoginParam {
    @NotNull(message = "手机号不能为空")
    @IsMobile()
    private String mobile;
    @NotNull(message="密码不能为空")
    private String password;
}
