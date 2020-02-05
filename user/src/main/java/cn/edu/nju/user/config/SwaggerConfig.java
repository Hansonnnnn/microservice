package cn.edu.nju.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * swagger reference
 * https://www.ibm.com/developerworks/cn/java/j-using-swagger-in-a-spring-boot-project/index.html
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        List<ResponseMessage> list = new ArrayList<>();
        list.add(new ResponseMessageBuilder()
                        .code(500)
                        .message("服务器发生异常")
                        .responseModel(new ModelRef("Error"))
                        .build());
        list.add(new ResponseMessageBuilder()
                .code(403)
                .message("资源不可用")
                .build());

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.edu.nju.user.rest"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.POST, list);
    }
}
