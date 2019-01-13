package com.yumo.common.test;

import com.yumo.common.android.YmContext;
import com.yumo.common.log.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by yumodev on 17/11/7.
 */

public class TestReflect {
    public static void printClassInfo(String className){
        try {
            Class cls = YmContext.class.getClassLoader().loadClass(className);
            printField(cls);
            printMethods(cls);
        }  catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void printClassInfo(Class cls){
        printField(cls);
        printMethods(cls);
    }

    public static void printField(Class cls){
        Field[] fields = cls.getFields();
        StringBuilder sb = new StringBuilder();
        for (Field field : fields){
            sb.append(field.getName());
        }
        Log.i(Log.LIB_TAG, "printFields:"+sb.toString());
    }

    public static void printMethods(Class cls){
        Method[] methods = cls.getMethods();
        StringBuilder sb = new StringBuilder();
        for (Method method : methods){
            sb.append(method.getName());
            int modifiers = method.getModifiers();
            sb.append(" isStatic():"+Modifier.isStatic(modifiers));
            sb.append("\n");
        }
        Log.i(Log.LIB_TAG, "printMethods:"+sb.toString());
    }
}
