package cn.edu.nju.eceureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EceurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EceurekaApplication.class, args);
    }

}
