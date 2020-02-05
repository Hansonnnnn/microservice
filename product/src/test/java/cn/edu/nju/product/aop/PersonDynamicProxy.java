package cn.edu.nju.product.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class PersonDynamicProxy implements InvocationHandler {
    private Object delegate;

    private static final Logger logger = LoggerFactory.getLogger(PersonDynamicProxy.class);

    public Object bind(Object delegate) {
        this.delegate = delegate;
        return Proxy.newProxyInstance(delegate.getClass().getClassLoader(), delegate.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result;
        try {
            logger.info("Before Proxy");
            result = method.invoke(delegate, args);
            logger.info("After Proxy");
        } catch (Exception e) {
            throw e;
        }
        return result;
    }

    public static void main(String[] args) {
        PersonDynamicProxy proxy = new PersonDynamicProxy();
        IPerson iPerson = (IPerson) proxy.bind(new Person());
        iPerson.doSomething();
    }
}
