package com.yumodev.java.thread;

import junit.framework.TestCase;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by yumodev on 7/18/16.
 * call Future
 */
public class TestCallFuture extends TestCase {
    private final String LOG_TAG = "TestCallFuture";

    public void testCallFuture(){
        Callable<Integer> call = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return new Random().nextInt(100);
            }
        };

        FutureTask<Integer> future = new FutureTask<>(call);

        new Thread(future).start();

        try {
            Thread.sleep(3000);
            System.out.println("future:"+future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
