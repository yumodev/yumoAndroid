package com.yumodev.java.patten.factory;

/**
 * Created by yumodev on 17/9/9.
 * 简单工厂模式
 */

public class SimpleFactoryDemo {
    /**
     * 抽象产品
     */
    public abstract class Product{
        public abstract void method();
    }

    /**
     * 具体产品
     */
    public class ConcreteProductA extends Product{
        @Override
        public void method() {
            System.out.println(getClass().getSimpleName()+".method");
        }
    }

    /**
     * 具体产品
     */
    public class ConcreteProductB extends Product{
        @Override
        public void method() {
            System.out.println(getClass().getSimpleName()+".method");
        }
    }


    /**
     * 具体工厂
     */
    public class ConcreteFactory {

        public Product createProduct(String name) {
            if ("A".equals(name)){
                return new ConcreteProductA();
            }else if ("B".equals(name)){
                return new ConcreteProductB();
            }
            return null;
        }
    }


    public void test(){
        ConcreteFactory factory = new ConcreteFactory();
        Product productA = factory.createProduct("A");
        productA.method();
        Product productB = factory.createProduct("B");
        productB.method();
    }


    public static void main(String args[]){
        (new SimpleFactoryDemo()).test();
    }
}
