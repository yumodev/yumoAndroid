package com.yumodev.java.advance;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;


/**
 * Created by yumodev on 17/9/18.
 */

public class ClassLoaderTest {

    /**
     * 获取类加载器的加载路径
     */
    public static void getClassLoaderPath(){
        System.out.println(ClassLoader.getSystemClassLoader());
        System.out.println(ClassLoaderTest.class.getClassLoader());

        System.out.println("打印AppClassLoader的加载路径");
        ClassLoader appClassLoader = ClassLoaderTest.class.getClassLoader();
        URL[] appUrls = ((URLClassLoader)appClassLoader).getURLs();
        printURLs(appUrls);


        System.out.println("打印ExtClassLoader类加载器的加载路径");
        ClassLoader extClassLoader = appClassLoader.getParent();
        URL[] extUrls = ((URLClassLoader)extClassLoader).getURLs();
        printURLs(extUrls);

        //
        ClassLoader loader = extClassLoader.getParent();
        if (loader == null){
            printURLs(printBootstrapClassPath());
        }
    }

    public static URL[] printBootstrapClassPath(){
        System.out.println("打印BootstrapClassLoader类加载器的加载路径");
        try {
            Class launcherClass = Class.forName("sun.misc.Launcher");
            Method bootstrapPathMethod = launcherClass.getDeclaredMethod("getBootstrapClassPath", new Class[0]);
            if (bootstrapPathMethod != null){
                Object object = bootstrapPathMethod.invoke(launcherClass, new Object[0]);
                Method getURLsMethod = object.getClass().getDeclaredMethod("getURLs", new Class[0]);
                return (URL[])getURLsMethod.invoke(object);
            }
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void printURLs(URL[] urls){
        if (urls == null){
            return;
        }

        for (URL url : urls){
            System.out.println("类加载器路径："+url.toString());
        }
    }

    public static void main(String[] args){
        getClassLoaderPath();
    }
}
