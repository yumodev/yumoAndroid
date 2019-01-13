package com.yumodev.java.patten.factory;

/**
 * Created by yumodev on 17/9/9.
 */

public class MoreFactoryDemo {
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
    public class ConcreteProductB extends Product {
        @Override
        public void method() {
            System.out.println(getClass().getSimpleName()+".method");
        }
    }

    /**
     * 抽象工厂
     */
    public abstract class Factory{
        /**
         * 创建工厂
         * @return
         */
        public abstract Product createProduct();
    }

    /**
     * 具体工厂
     */
    public class ConcreteFactoryA extends Factory{
        @Override
        public Product createProduct() {
            return new ConcreteProductA();
        }
    }

    /**
     * 具体工厂
     */
    public class ConcreteFactoryB extends Factory{
        @Override
        public Product createProduct() {
            return new ConcreteProductB();
        }
    }


    public void test(){
        Factory factory = new ConcreteFactoryA();
        Product productA = factory.createProduct();
        productA.method();

        Factory factoryB = new ConcreteFactoryB();
        Product productB = factoryB.createProduct();
        productB.method();
    }


    public static void main(String args[]){
        (new MoreFactoryDemo()).test();
    }
}
