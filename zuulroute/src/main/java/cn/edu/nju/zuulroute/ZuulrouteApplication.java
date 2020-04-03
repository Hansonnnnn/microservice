package cn.edu.nju.zuulroute;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class ZuulrouteApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulrouteApplication.class, args);
    }

}
