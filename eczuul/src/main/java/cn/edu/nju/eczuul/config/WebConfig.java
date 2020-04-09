//package cn.edu.nju.eczuul.config;
//
//import cn.edu.nju.eczuul.filter.SessionExpireFilter;
//import cn.edu.nju.eczuul.interceptor.AuthorityInterceptor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.filter.DelegatingFilterProxy;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
//import javax.servlet.Filter;
//
//@Configuration
//public class WebConfig extends WebMvcConfigurerAdapter {
//    @Autowired
//    AuthorityInterceptor authorityInterceptor;
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        // addPathPatterns 用于添加拦截规则
//        // excludePathPatterns 用户排除拦截
//        // 映射为 user 的控制器下的所有映射
//        //registry.addInterceptor(authorityInterceptor).addPathPatterns("/user/login").excludePathPatterns("/index", "/");
//        registry.addInterceptor(authorityInterceptor);
//        super.addInterceptors(registry);
//    }
//
//    @Bean("myFilter")
//    public Filter uploadFilter() {
//        return new SessionExpireFilter();
//    }
//
//    @Bean
//    public FilterRegistrationBean uploadFilterRegistration() {
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        registration.setFilter(new DelegatingFilterProxy("myFilter"));
//        registration.addUrlPatterns("/**");
//        registration.setName("MyFilter");
//        registration.setOrder(1);
//        return registration;
//    }
//
//
//}
