package com.yumodev.java.airthmetic.leetcode;

/**
 * Created by wks on 8/5/16.
 * You are given two linked lists representing two non-negative numbers.
 * The digits are stored in reverse order and each of their nodes contain a single digit.
 * Add the two numbers and return it as a linked list.
 * <p>
 * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
 * Output: 7 -> 0 -> 8
 */
public class Leetcode_0002_AddTwoNumbers {

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int lso = 0;
        int sum = 0;
        ListNode ls = null;
        ListNode nextLn = null;
        while (l1 != null || l2 != null || lso != 0) {
            sum = lso;
            if (l1 != null) sum += l1.val;
            if (l2 != null) sum += l2.val;

            lso = sum / 10;
            ListNode temp = new ListNode(sum % 10);
            if (ls == null) nextLn = ls = temp;
            else{
                nextLn.next = temp;
                nextLn = nextLn.next;
            }

            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }
        return ls;
    }

    public static ListNode initListNode(int num) {
        int mod = 0;
        int temp = num;
        ListNode ln = null;
        ListNode nextLn = null;
        while (temp > 0) {
            mod = temp % 10;
            temp = temp / 10;
            if (ln == null){
                nextLn = ln = new ListNode(mod);
            }else{
                nextLn.next = new ListNode(mod);
                nextLn = nextLn.next;
            }
        }

        return ln;
    }

    public static String ListNodeToString(final ListNode listNode) {
        StringBuilder sb = new StringBuilder();
        ListNode ln = listNode;
        while (ln != null) {
            sb.append(ln.val + " ");
            ln = ln.next;
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        ListNode l1 = initListNode(1027633061);
        ListNode l2 = initListNode(1696698036);
//
//        ListNode l1 = initListNode(342);
//        ListNode l2 = initListNode(465);

//        ListNode l1 = initListNode(5);
//        ListNode l2 = initListNode(5);
        System.out.println("listNode1 " + ListNodeToString(l1));

        System.out.println("listNode2 " + ListNodeToString(l2));

        ListNode ls = addTwoNumbers(l1, l2);
        System.out.println("listNodes " + ListNodeToString(ls));
    }
}
