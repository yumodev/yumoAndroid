package com.yumodev.ui.module.stack;

/**
 * Created by yumodev on 18/1/10.
 */

public class StackEntry {
    public String title;
    public int titleIconColor;
    public int color;

    public static StackEntry newInstance(String title){
        StackEntry data = new StackEntry();
        data.title = title;
        return data;
    }
}
