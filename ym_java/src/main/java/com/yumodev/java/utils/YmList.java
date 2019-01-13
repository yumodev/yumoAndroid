package com.yumodev.java.utils;

/**
 * Created by yumodev on 18/2/5.
 */

public class YmList<T> {

    private Node<T> mHead = null;

    public void add(T t){
        if (mHead == null){
            mHead = new Node<>(t);
            return;
        }

        Node<T> node = new Node<>(t);
        Node<T> head = mHead;
        while (head.next != null){
            head = head.next;
        }
        head.next = node;
    }

    public static class Node<T>{
        T value;
        Node<T> next;

        Node(T value){
            this.value = value;
        }
    }




}
