package cn.edu.nju.zuulroute.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MicroserviceZuulConfig {

    @Autowired
    ZuulProperties zuul;

    @Autowired
    ServerProperties servers;

    @Bean
    public MicroserviceRouteLocator routeLocator() {
        return new MicroserviceRouteLocator(servers.getServlet().getContextPath(), this.zuul);
    }
}
