package com.yumodev.java.airthmetic.leetcode;

/**
 * Created by yumodev on 9/13/16.
 * Merge two sorted linked lists and return it as a new list.
 * The new list should be made by splicing together the nodes of the first two lists
 */
public class Leetcode_0021_MergeTwoSortLists {

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null ){
            return l2;
        }

        if (l2 == null){
            return l1;
        }

        ListNode ln;
        ListNode head;
        if (l1.val < l2.val){
            head = ln = l1;
            l1 = l1.next;
        }else{
            head = ln = l2;
            l2 = l2.next;
        }

        while (l1 != null || l2 != null){
            if (l1 == null){
                ln.next = l2;
                break;
            }else if (l2 == null){
                ln.next = l1;
                break;
            }

            if (l1.val < l2.val){
                ln.next = l1;
                ln = ln.next;
                l1 = l1.next;
            }else{
                ln.next = l2;
                ln = ln.next;
                l2 = l2.next;
            }
        }

        return head;
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
        ListNode l1 = initListNode(54321);
        ListNode l2 = initListNode(54321);
        long startTime = System.nanoTime();
        ListNode l3 = mergeTwoLists(l1, l2);
        long endTime = System.nanoTime();
        long time = endTime - startTime;

        System.out.println("removeNthFromEnd:" + ListNodeToString(l3) + " time:" + time);
    }
}
