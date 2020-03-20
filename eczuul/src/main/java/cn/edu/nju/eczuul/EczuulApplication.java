package cn.edu.nju.eczuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class EczuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(EczuulApplication.class, args);
    }

}
