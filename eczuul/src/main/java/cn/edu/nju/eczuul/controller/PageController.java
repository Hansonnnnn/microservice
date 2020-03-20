package cn.edu.nju.eczuul.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    @RequestMapping("login")
    public String loginPage(){
        return "login";
    }
}
