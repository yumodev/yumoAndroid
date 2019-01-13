package com.yumodev.java.airthmetic.offer;


import java.util.Stack;

/**
 * Created by yumodev on 16/10/14.
 */
public class TestList {

    public static void main(String[] args){
        //new PrintListReversing().testPrintListReversing();
        //new DeleteNodeInList().testDeleteNodeInList();
        //new FindLastKthFromTail().testFindLastKthFromTail();
        //new ReverseList().testReverseList();
        new MergeSortList().testMergeSortList();
    }

    public static class ListNode{
        public int mValue;
        public ListNode mNext = null;

        public ListNode(){

        }

        public ListNode(int value){
            mValue = value;
        }
    }

    public static ListNode initList(int[] arr){
        if (arr == null || arr.length == 0){
            return null;
        }

        ListNode head = new ListNode(arr[0]);
        ListNode tailNode = head;
        for (int i = 1; i < arr.length; i++){
            ListNode node = new ListNode(arr[i]);
            tailNode.mNext = node;
            tailNode = node;
        }

        return head;
    }

    public static void printList(ListNode head){
        ListNode node = head;
        while (node != null){
            System.out.println(node.mValue);
            node = node.mNext;
        }
    }



    /*****************************************
     剑指Offer5 从尾到头打印链表
     题目:输入一个链表的头结点,从尾到头反过来打印出每个节点的值.
     *****************************************/

    public static class PrintListReversing{
        public void testPrintListReversing(){
            ListNode head = initList(new int[]{1,2,3,4,5,6});
            printListReversing(head);
            printListReversingRecursion(head);
        }

        public void printListReversing(ListNode head){
            Stack<ListNode> stack = new Stack<>();
            ListNode node = head;
            while (node.mNext != null){
                stack.push(node);
                node = node.mNext;
            }

            while (!stack.isEmpty()){
                System.out.println(stack.pop().mValue);
            }
        }

        public void printListReversingRecursion(ListNode node){
            if (node.mNext != null){
                printListReversing(node.mNext);
                System.out.println(node.mValue);
            }
        }
    }

    /*****************************************
     剑指Offer13 在O(1)时间内删除链表结点
     题目:给定单词链表的头指针和一个结点指针,
     定义一个函数在O(1)时间删除该结点,.
     *****************************************/

    public static class DeleteNodeInList{
        public void testDeleteNodeInList(){
            ListNode head = initList(new int[]{1,2});
            deleteNodeInList(head, head.mNext);
            printList(head);
        }

        public void deleteNodeInList(ListNode head, ListNode node){
            if (head == null || node == null){
                throw new IllegalArgumentException("args not null");
            }

            if (node.mNext != null){
                ListNode nextNode = node.mNext;
                node.mValue = nextNode.mValue;
                node.mNext = nextNode.mNext;
            }else if (node.equals(head)){
                head = null;
                node = null;
            }else if (node.mNext == null){
                ListNode before = head;
                while (before.mNext != node){
                    before = before.mNext;
                }

                before.mNext = null;
            }
        }
    }


    /*****************************************
     剑指Offer15 链表中倒数第K个结点.
     题目: 输入一个链表,输出该链表中倒数第K个结点.为了符合
     大多数人的习惯,本题从1开始计数,即链表的尾结点是倒数第1个结点
     .例如链表中有6个节点,从头结点开始他们的值依次为1,2,3,4,5,6,
     那么这个链表中的倒数第3个节点的值是4的节点.
     *****************************************/
    public static class FindLastKthFromTail{
        public void testFindLastKthFromTail(){
            ListNode head = initList(new int[]{1,2,3,4,5,6});
            ListNode node = findLastKthFromTail(head, 3);
            System.out.println(node.mValue);
        }

        private ListNode findLastKthFromTail(ListNode head, int k){
            if (head == null || k <=0){
                throw new IllegalArgumentException("args is bad");
            }

            ListNode before = head;
            ListNode kNode = null;
            int cur = 0;
            while (before != null){
                if (cur < k){
                    cur++;

                }else if (cur == k){
                    kNode = head;
                    cur++;

                }else{
                    kNode = kNode.mNext;
                }
                before = before.mNext;
            }

            return kNode;
        }
    }


    /*****************************************
     剑指Offer16 反转链表
     题目:输入一个函数,输入一个链表的头结点,
     反转该链表,并输入翻转后链表的头节点
     *****************************************/
    public static class ReverseList{

        public static void testReverseList(){
            ListNode head = initList(new int[]{1,2,3,4,5,6});
            ListNode newHead = reverseList(head);
            printList(newHead);
        }

        public static ListNode reverseList(ListNode head){
            if (head == null || head.mNext == null){
                return head;
            }

            ListNode temp = null;
            ListNode next = null;

            while (head.mNext != null){
                next = head.mNext;
                head.mNext = temp;
                temp = head;
                head = next;
            }

            head.mNext = temp;
            return head;
        }
    }

    /*****************************************
     剑指Offer17 合并两个排序链表
     题目:输入两个递增排序的链表,合并这两个链表,并使
     新链表中的节点仍然是按照递增排序的.比如输入
     {1,3,5,7},{2,4,6,8} 合并后是{1,2,3,4,5,6,7,8}
     *****************************************/
    public static class MergeSortList{
        public void testMergeSortList(){
            ListNode l1 = initList(new int[]{1,3,5,7});
            ListNode l2 = initList(new int[]{2,4,6,8});
            printList(mergeSortList(l1,l2));
        }

        public ListNode mergeSortList(ListNode l1, ListNode l2){
            if (l1 == null && l2 == null){
                throw new IllegalArgumentException("args should not be null");
            }

            if (l1 == null){
                return l2;
            }

            if (l2 == null){
                return l1;
            }

            ListNode node = null;
            ListNode head = null;

            if (l1.mValue <= l2.mValue){
                head = l1;
                l1 = l1.mNext;
            }else{
                head = l2;
                l2 = l1.mNext;
            }

            node = head;

            while (l1 != null && l2 != null){
                if (l1.mValue <= l2.mValue){
                    node.mNext = l1;
                    l1 = l1.mNext;
                }else{
                    node.mNext = l2;
                    l2 = l2.mNext;
                }

                node = node.mNext;

                if (l1 == null){
                    node.mNext = l2;
                }else if (l2 == null){
                    node.mNext = l1;
                }
            }

            return head;
        }
    }
}
