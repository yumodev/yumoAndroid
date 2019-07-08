package com.yumo.demo.listener;

import java.util.Observable;

/**
 * Created by yumodev on 17/11/15.
 */

public class UpdateTitleObservable extends Observable {

    private UpdateTitleObservable(){}

    public static UpdateTitleObservable getInstance(){
        return UpdateTitleObservableHolder.mInstance;
    }

    public void setTitle(String title){
        setChanged();
        notifyObservers(title);
    }

    private static class UpdateTitleObservableHolder{
        private static final UpdateTitleObservable mInstance = new UpdateTitleObservable();
    }
}
