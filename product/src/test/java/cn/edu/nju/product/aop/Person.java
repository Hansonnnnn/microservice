package cn.edu.nju.product.aop;

public class Person implements IPerson {
    @Override
    public void doSomething() {
        System.out.println("I want to sell my house!");
    }
}
