package com.yumodev.java.airthmetic.list;


/**
 * Created by yumodev on 18/2/8.
 * 返回单链表中倒数第K个数
 */

public class ReturnLastKNumList {
    public static class Node<T>{
        T value;
        Node<T> next;

        Node(T value){
            this.value = value;
        }
    }

    public static Integer returnLastKNumList(Node<Integer> head, int k){
        Node<Integer> last = head;

        while (head != null ){
            head = head.next;
            if (last != null && k <= 0){
                last = last.next;
            }
            k--;
        }
        return last.value;
    }

    public static void test(){
        Node<Integer> head = new Node<>(0);
        Node<Integer> node1 = new Node<>(1);
        Node<Integer> node2 = new Node<>(2);
        Node<Integer> node3 = new Node<>(3);
        Node<Integer> node4 = new Node<>(4);

        head.next = node1;
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;

        Node<Integer> temp = head;
        while (temp != null){
            System.out.println(temp.value);
            temp = temp.next;
        }

        long startTime = System.nanoTime();
        int value = returnLastKNumList(head, 0);
        long endTime = System.nanoTime();
        System.out.println(String.format("time :" + (endTime - startTime)+" result:")+value);

    }


    public static void main(String[] args){
        test();
    }

}
