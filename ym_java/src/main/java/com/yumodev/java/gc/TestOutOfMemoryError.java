package com.yumodev.java.gc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yumodev on 10/2/16.
 */
public class TestOutOfMemoryError {
    public static class OOMObject{


    }

    public static void testOutOfMemory(){
        List<OOMObject> list = new ArrayList<>();
        while (true){
            list.add(new OOMObject());
        }
    }

    public static void main(String[] args){
        testOutOfMemory();
    }
}
