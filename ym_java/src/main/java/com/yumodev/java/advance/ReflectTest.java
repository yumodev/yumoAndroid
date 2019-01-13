package com.yumodev.java.advance;


import com.yumodev.java.entry.Book;

import junit.framework.TestCase;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Java反射的学习
 */
public class ReflectTest extends TestCase{

    public ReflectTest() {
    }

    public void testGetClass(){
        Book book = new Book();
        System.out.println("通过实例获取类对象"+book.getClass().getName());
        System.out.println("通过.class获取类的对象"+Book.class.getName());
        try {
            System.out.println("通过Class.forName获取类对象"+Class.forName("com.yumodev.java.entry.Book").getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印一个类的相关信息
     */
    public void testPrintClass(){
        Class cls = Book.class;
        printClass(cls);
    }

    /**
     * 打印一个类的生命的所有成员变量，不包含接口和继承的
     */
    public void testPrintField(){
        try {
            Class cls = Class.forName("com.yumodev.java.entry.Book").asSubclass(Book.class);
            //Class cls = (new Book("", 1.0f)).getClass();

            System.out.println("\n"+printFields(cls.getDeclaredFields()));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印方法,包含接口和继承的
     */
    public void testPrintMethods(){
        Class cls = (new Book("", 1.0f)).getClass();
        System.out.println("\n"+printMethods(cls.getMethods()));
    }

    /**
     * 打印构造方法
     */
    public void testPrintConstructors(){
        Class cls = (new Book("", 1.0f)).getClass();
        System.out.println("\n"+printConstructors(cls.getConstructors()));
    }

    /**
     * 通过构造方法生成一个新的实例
     */
    public void testNewInstance(){
        Class cls = Book.class;
        try {
            Constructor constructor = cls.getConstructor(String.class, double.class);
            Book book = (Book) constructor.newInstance("《Java入门详解》", 990f);
            System.out.println("通过构造方法创建实例成功了:"+book.toString());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void printClass(Class cls){
        System.out.println("getName():"+cls.getName());
        System.out.println("getTypeName:"+cls.getTypeName());

        System.out.println("getSimpleName()"+cls.getSimpleName());
        // 返回 Java Language Specification 中所定义的底层类的规范化名
        System.out.println("getCanonicalName():"+cls.getCanonicalName());
        System.out.println("getPackage() :"+cls.getPackage());

        //返回组件类型
        System.out.println("getComponentType():"+ cls.getComponentType());

        System.out.println("getSigners() :"+cls.getSigners());
        // 返回父类
        System.out.println("SuperClass"+cls.getSuperclass());
        System.out.println("getGenericInterfaces():"+cls.getGenericInterfaces());
        System.out.println("getInterfaces():"+cls.getInterfaces());
        int modifiers = cls.getModifiers();
        System.out.println("isAbstract() :"+Modifier.isAbstract(modifiers));

        cls.isInterface();
        System.out.println("\n");
    }

    private String formatClassArray(Class[] clses){
        StringBuilder sb = new StringBuilder();
        for (Class cls : clses){
            sb.append(","+cls.toString());
        }
        return sb.toString();
    }

    private String printFields(Field[] fields){
        StringBuilder sb = new StringBuilder();
        for (Field field : fields){
            sb.append("\nname:"+field.getName());
            sb.append("\ngetType():"+field.getType().getTypeName());
            //获取修饰符
            sb.append("\ngetModifiers():"+field.getModifiers());
            sb.append("\ngetDeclaringClass():"+field.getDeclaringClass().getTypeName());
            sb.append("\ntoString:"+field.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    private String printMethods(Method[] methods){
        StringBuilder sb = new StringBuilder();
        for (Method method : methods){
            sb.append("\nname:"+method.getName());
            //sb.append("\ngetType():"+method.get);
            //获取修饰符
            sb.append("\ngetModifiers():"+method.getModifiers());
            sb.append("\ngetParameterTypes:"+formatClassArray(method.getParameterTypes()));
            sb.append("\ntoString:"+method.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    private String printConstructors(Constructor[] constructors){
        StringBuilder sb = new StringBuilder();
        for (Constructor method : constructors){
            sb.append("\nname:"+method.getName());
            //sb.append("\ngetType():"+method.get);
            //获取修饰符
            sb.append("\ngetModifiers():"+method.getModifiers());
            sb.append("\ngetParameterTypes:"+formatClassArray(method.getParameterTypes()));
            sb.append("\ntoString:"+method.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    public void testInvokeMethod(){
        Book book = Book.newBook("《Book》", 10f);
        Class cls = book.getClass();
        try {
            Method method = cls.getMethod("setPrice", double.class);
            method.invoke(book, 20f);
            System.out.println(book.getPrice());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void testInvokeStaticMethod(){
        Class cls = Book.class;
        try {
            Method method = cls.getMethod("newBook", String.class, double.class);
            Book book = (Book) method.invoke(null, "《Book》", 20f);
            System.out.println(book.toString());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void testInvokeField(){
        Book book = Book.newBook("《Book》", 10f);
        Class cls = book.getClass();
        try {
            Field name = cls.getField("name");
            name.set(book, "new book");
            System.out.println(book.getPrice());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void testInvokePrivateField(){
        Book book = Book.newBook("《Book》", 10f);
        Class cls = book.getClass();
        try {
            Field count = cls.getDeclaredField("count");
            //修改器访问属性
            count.setAccessible(true);
            count.set(null, 2);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }



    @Test
    public void testBook(){
        Book book = new Book("Android高级编程", 74);
        //获取book对象
        Class clsBook = book.getClass();
        try {
            //获取book对象的，name字段
            Field name = clsBook.getDeclaredField("name");
            //获取并打印name的值
            System.out.println("通过反射获取的书名为：" + name.get(book));
            //修改name的值
            System.out.println("通过反射获取的将书名修改为：android 开发入门详解");
            name.set(book, "android 开发入门详解");
            //输出修改后的name的值
            System.out.println("通过反射修改后书名为：" + book.name);

            //通过反射调用方法
            Method getPrice = clsBook.getMethod("getPrice", new Class[0]);
            getPrice.invoke(book);
            System.out.println("现在书的价格为:" + book.getPrice());

            Double price = 100.0;
            Class[] argsCls = new Class[1];
            argsCls[0] = price.getClass();

            Method setPrice = clsBook.getMethod("setPrice", argsCls);
            setPrice.invoke(book, price);
            System.out.println("通过反射通过反射将书的价格修改:" + price);

            getPrice = clsBook.getMethod("getPrice", new Class[0]);
            getPrice.invoke(book);
            System.out.println("通过反射修改后，书的价格为:" + book.getPrice());
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testDate(){
        printClass("java.util.Date");
    }


    /**
     * 给定一个类名，打印它的属性，方法等等。
     * yumodev
     *
     * @param className void
     * 2015-1-4
     */
    public static void printClass(String className) {
        if (className == null || className.length() <= 0) return;

        try {
            //返回与带有给定字符串名的类或接口相关联的 Class 对象。
            Class cls = Class.forName(className);
            //一个字符串的权限。
            String modifiers = Modifier.toString(cls.getModifiers());
            System.out.print(modifiers);
            System.out.print(cls.getName());
            Class superCls = cls.getSuperclass();
            if (superCls != null) System.out.print(" extends " + superCls.getName());
            System.out.println("{");

            //打印field 成员变量
            Field[] fields = cls.getFields();
            for (Field field : fields) {
                //一个字符串的权限。
                modifiers = Modifier.toString(field.getModifiers());
                System.out.print(modifiers + " ");
                //打印类型
                System.out.print(field.getType() + " ");
                //打印方法的名字
                System.out.print(field.getName());

                System.out.println();
            }

            //打印methods
            Method[] methods = cls.getMethods();
            for (Method method : methods) {
                //一个字符串的权限。
                modifiers = Modifier.toString(method.getModifiers());

                System.out.print(modifiers + " ");
                //打印返回类型
                System.out.print(method.getReturnType().getName() + " ");
                //打印方法的名字
                System.out.print(method.getName() + "(");
                //打印参数
                Class[] parameters = method.getParameterTypes();
                for (int i = 0; i < parameters.length; i++) {
                    if (i > 0) System.out.print(",");
                    Class para = parameters[i];
                    System.out.print(para.getName());
                }

                System.out.println(")");
            }

            System.out.println("}");


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
