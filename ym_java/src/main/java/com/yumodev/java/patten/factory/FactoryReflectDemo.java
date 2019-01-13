package com.yumodev.java.patten.factory;

/**
 * Created by yumodev on 17/9/9.
 */

public class FactoryReflectDemo {
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
        public ConcreteProductA(){

        }
        @Override
        public void method() {
            System.out.println(getClass().getSimpleName()+".method");
        }
    }

    /**
     * 具体产品
     */
    public class ConcreteProductB extends Product {
        public ConcreteProductB(){

        }
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
        public abstract <T extends Product> T createProduct(Class<T> cls);
    }

    /**
     * 具体工厂
     */
    public class ConcreteFactory extends Factory{

        @Override
        public <T extends Product> T createProduct(Class<T> cls) {
            T product = null;
            try {
                //内部类youbug。
                product = (T)(Class.forName(cls.getName()).newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            return product;
        }
    }


    public void test(){
        Factory factory = new ConcreteFactory();
        Product productA = factory.createProduct(ConcreteProductA.class);
        productA.method();

        Product productB = factory.createProduct(ConcreteProductB.class);
        productB.method();
    }


    public static void main(String args[]){
        (new FactoryReflectDemo()).test();
    }
}
