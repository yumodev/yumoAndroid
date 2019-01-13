package com.yumodev.java.airthmetic.list;

/**
 * Created by yumodev on 18/2/5.
 * 反转双向列表，反转双向列表
 * 双链表的反转，只需要交换每个节点的prev和next指向。
 */

public class ReverseDoubleList {

    public static class Node<T>{
        T value;
        Node<T> next;
        Node<T> prev;

        Node(T value){
            this.value = value;
        }
    }

    public static Node reverseDoubleList(Node head){
        Node last = null;
        while (head != null){
            last = head;
            head = last.next;
            last.next = last.prev;
            last.prev = head;
        }
        return last;
    }

    public static void test(){
        Node<Integer> head = new Node<>(0);
        Node<Integer> node1 = new Node<>(1);
        Node<Integer> node2 = new Node<>(2);
        Node<Integer> node3 = new Node<>(3);
        Node<Integer> node4 = new Node<>(4);

        head.next = node1;
        node1.prev = head;
        node1.next = node2;
        node2.prev = node1;
        node2.next = node3;
        node3.prev = node2;
        node3.next = node4;
        node4.prev = node3;

        Node<Integer> temp = head;
        while (temp != null){
            System.out.println(temp.value);
            temp = temp.next;
        }

        long startTime = System.nanoTime();
        head = reverseDoubleList(head);
        long endTime = System.nanoTime();
        System.out.println(String.format("time :" + (endTime - startTime)+" result:"));

        temp = head;
        while (temp != null){
            System.out.println(temp.value);
            temp = temp.next;
        }

    }


    public static void main(String[] args){
        test();
    }
}
