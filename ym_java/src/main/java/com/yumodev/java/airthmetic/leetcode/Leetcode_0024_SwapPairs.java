package com.yumodev.java.airthmetic.leetcode;

/**
 * Created by yumodev on 9/13/16.
 */
public class Leetcode_0024_SwapPairs {

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public static ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null){
            return head;
        }

        int n = 1;

        ListNode ln = new ListNode(0);
        ln.next = head;
        head = head.next;
        ListNode temp;
        while (true){
            if (n % 2 == 0){
                temp = ln.next;
                ln.next = ln.next.next;
                temp.next = ln.next.next;
                ln.next.next = temp;
                ln = ln.next.next;
            }

            if (ln.next == null || ln.next.next == null){
                break;
            }

            n++;
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
        ListNode l1 = initListNode(4321);
        //ListNode l1 = initListNode(321);
        long startTime = System.nanoTime();
        ListNode l2 = swapPairs(l1);
        long endTime = System.nanoTime();
        long time = endTime - startTime;

        System.out.println("removeNthFromEnd:" + ListNodeToString(l2) + " time:" + time);
    }

}
