package com.yumodev.java.airthmetic.list;

import java.util.Stack;

/**
 * Created by yumodev on 18/2/5.
 * 反转单向列表，反转双向列表
 * 如果链表长度为N，时间复杂度要求为O(N),额外的空间复杂度要求为O(1)
 */

public class Reverselist {

    public static class Node<T>{
        T value;
        Node<T> next;

        Node(T value){
            this.value = value;
        }
    }

    public static Node reverseList(Node head){
        Node tempHead = head;
        Node next = head;

        while (next.next != null){
            Node node1 = next.next.next;
            Node node2 = next.next;
            node2.next = tempHead;
            tempHead = next.next;
            next.next = node1;

        }
        return tempHead;
    }


    public static Node reverseList1(Node head){
        Node prev = null;
        Node next = null;
        while (head != null){
            next = head.next;
            head.next = prev;
            prev = head;
            head = next;

        }
        return prev;
    }

    /**
     * 利用栈的方式实现
     * @param head
     * @return
     */
    public static Node reverseByStack(Node head){
        Stack<Node> stack = new Stack<>();
        while (head != null){
            stack.push(head);
            head = head.next;
        }

        head = stack.pop();
        Node temp = head;
        while (stack.size() > 0){
            temp.next = stack.pop();
            temp = temp.next;
        }
        temp.next = null;

        return head;
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
        head = reverseByStack(head);
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
