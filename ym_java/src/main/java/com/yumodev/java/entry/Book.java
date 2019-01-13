package com.yumodev.java.entry;

/**
 * Created by yumodev
 * Book类,目前用于反射测试
 */
public class Book {
    /**
     * 书的名字
     */
    public String name = "";
    /**
     * 书的价格
     */
    private double price = 0.0;
    /**
     * 创建书的数量，静态变量
     */
    private static int count;

    /**
     * 默认构造方法
     */
    public Book(){
        count++;
    }

    /**
     * 构造方法
     * @param name
     * @param price
     */
    public Book(String name, double price) {
        this.name = name;
        this.price = price;
        count++;
    }

    /**
     * 通过静态工厂的创建Book实例
     * @param name
     * @param price
     * @return
     */
    public static Book newBook(String name, double price){
        Book book = new Book();
        book.name = name;
        book.price = price;
        count++;
        return book;
    }

    @Override
    public String toString() {
        return super.toString()+" "+name+" 价格："+price;
    }

    /**
     * 获取价格
     * @return
     */
    public double getPrice() {
        return price;
    }

    /**
     * 设置新的价格
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * 获取图书的数量。
     * @return
     */
    public static int getCount(){
        return count;
    }
}
