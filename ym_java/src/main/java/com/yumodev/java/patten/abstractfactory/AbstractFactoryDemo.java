package com.yumodev.java.patten.abstractfactory;

/**
 * Created by yumodev on 17/9/9.
 */

public class AbstractFactoryDemo {

    /**
     * 抽象产品A
     */
    public abstract class AbstractProductA{
        public abstract void method();
    }

    /**
     * 抽象产品B
     */
    public abstract class AbstractProductB{
        public abstract void method();
    }

   /**
    * 具体产品A1
    */
    public class ConcreteProductA1 extends AbstractProductA{

        @Override
        public void method() {
            System.out.println(getClass().getSimpleName()+".method");
        }
    }
    /**
     * 具体产品A2
     */
    public class ConcreteProductA2 extends AbstractProductA{

        @Override
        public void method() {
            System.out.println(getClass().getSimpleName()+".method");
        }
    }

    /**
     * 具体产品B1
     */
    public class ConcreteProductB1 extends AbstractProductB{

        @Override
        public void method() {
            System.out.println(getClass().getSimpleName()+".method");
        }
    }

    /**
     * 具体产品B2
     */
    public class ConcreteProductB2 extends AbstractProductB{

        @Override
        public void method() {
            System.out.println(getClass().getSimpleName()+".method");
        }
    }

    /**
     * 抽象工厂
     */
    public abstract class AbstractFactory{
        public abstract AbstractProductA createProductA();
        public abstract AbstractProductB createProductB();
    }

    /**
     * 具体的工厂1
     */
    public class ConcreteFactory1 extends AbstractFactory{

        @Override
        public AbstractProductA createProductA() {
            return new ConcreteProductA1();
        }

        @Override
        public AbstractProductB createProductB() {
            return new ConcreteProductB1();
        }
    }

    /**
     * 具体的工厂2
     */
    public class ConcreteFactory2 extends AbstractFactory{

        @Override
        public AbstractProductA createProductA() {
            return new ConcreteProductA2();
        }

        @Override
        public AbstractProductB createProductB() {
            return new ConcreteProductB2();
        }
    }

    public void test(){
        AbstractFactory factory1 = new ConcreteFactory1();
        factory1.createProductA().method();
        factory1.createProductB().method();

        AbstractFactory factory2 = new ConcreteFactory2();
        factory2.createProductA().method();
        factory2.createProductB().method();
    }

    public static void main(String[] args){
        (new AbstractFactoryDemo()).test();
    }
}
