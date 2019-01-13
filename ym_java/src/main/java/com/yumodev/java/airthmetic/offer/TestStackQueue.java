package com.yumodev.java.airthmetic.offer;

import java.util.Stack;

/**
 * Created by yumodev on 16/10/14.
 */
public class TestStackQueue {

    public static void main(String[] args){
        new StackQueue().test();
    }


    public static class QueueNode{

    }
    /*****************************************
     剑指Offer7 用两个栈实现队列
     题目:用两个栈实现一个队列,队列的声明如下.请实现它的两个函数appendTail
     和deleteHead,分别完成在低劣尾部插入节点和在低劣头部删除节点的功能.
     *****************************************/

    public static class StackQueue{
        Stack<Integer> stack1 = new Stack<>();
        Stack<Integer> stack2 = new Stack<>();

        public void test(){
            StackQueue queue = new StackQueue();
            for (int i = 0; i < 10; i++){
                queue.appendTail(i);
            }

            for (int i =0; i < 10; i++){
                System.out.println(queue.deleteHead());
            }

        }

        public void appendTail(int value){
            stack1.push(value);
        }

        public int deleteHead(){
            if (stack2.isEmpty()){
                while (!stack1.isEmpty()){
                    stack2.add(stack1.pop());
                }
            }

            if (stack2.isEmpty()){
                throw new RuntimeException("no more element");
            }

            return stack2.pop();
        }

    }
}
