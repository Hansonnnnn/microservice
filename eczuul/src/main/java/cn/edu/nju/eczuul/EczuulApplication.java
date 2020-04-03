package cn.edu.nju.eczuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
@EntityScan({"entity.order", "cn.edu.nju.eczuul.entity"})
public class EczuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(EczuulApplication.class, args);
    }

}
