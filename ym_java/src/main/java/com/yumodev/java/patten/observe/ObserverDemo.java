package com.yumodev.java.patten.observe;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by yumodev on 16/10/17.
 * 观察者模式.
 */
public class ObserverDemo {


    public static void main(String[] args){
        TestProvider provider = new TestProvider();

        provider.addObserver(new TestObserver());
        provider.addObserver(new TestObserver());

        provider.testChanged();
    }

    static class TestProvider extends Observable{

        public void testChanged(){
            setChanged();
            notifyObservers();
        }
    }

    static class TestObserver implements Observer{

        @Override
        public void update(Observable o, Object arg) {
            System.out.println(TestObserver.this.toString());
        }
    }
}
