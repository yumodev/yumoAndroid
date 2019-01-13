package com.yumo.android.test.io;

import android.util.Log;

import com.yumo.common.io.YmAdFileUtil;
import com.yumo.demo.view.YmTestFragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by yumo on 5/2/16.
 */
public class SerializableTest extends YmTestFragment {
    private final String LOG_TAG = "SerializableTest";

    static class Person implements Serializable {
        private static final long serialVersionUID = 1L;
        public String mName = "";
        public int mAge = 10;

        @Override
        public String toString() {
            return super.toString()+" mName:"+mName+" age:"+mAge;
        }
    }

    private String getFileName(){
        return YmAdFileUtil.getFileCache(getContext(), null)+ File.separator+"obj.txt";
    }

    public void testSaveSerializable() {
        Person person = new Person();
        person.mName = "wks";
        person.mAge = 100;

        try {
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream(getFileName()));
            Log.d(LOG_TAG, person.toString());
            out.writeObject(person);
            out.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testReadSeriablizable(){
        Person person = new Person();
        try {
            ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream(getFileName()));
            person = (Person) in.readObject();
            Log.d(LOG_TAG, person.toString());
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
