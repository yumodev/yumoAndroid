package com.yumodev.java.patten.factory;

/**
 * Created by yumodev on 17/9/9.
 */

public class FactoryDemo {
    /**
     * 抽象产品
     */
    public abstract class Product{
        public abstract void method();
    }

    /**
     * 具体产品
     */
    public class ConcreteProduct extends Product{
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
    public class ConcreteFactory extends Factory{
        @Override
        public Product createProduct() {
            return new ConcreteProduct();
        }
    }


    public void test(){
        Factory factory = new ConcreteFactory();
        Product product = factory.createProduct();
        product.method();
    }


    public static void main(String args[]){
        (new FactoryDemo()).test();
    }
}
