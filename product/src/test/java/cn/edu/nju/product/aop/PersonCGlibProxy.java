package cn.edu.nju.product.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class PersonCGlibProxy implements MethodInterceptor {
    private Object delegate;
    private static final Logger logger = LoggerFactory.getLogger(PersonCGlibProxy.class);

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        return null;
    }

    public static Person getProxyInstance() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Person.class);
        enhancer.setCallback(new PersonCGlibProxy());
        return (Person) enhancer.create();
    }
}
