package com.yumodev.java.airthmetic.leetcode;

/**
 * Created by yumodev on 9/13/16.
 */
public class Leetcode_0019_RemoveNthFromEnd {

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public static ListNode removeNthFromEnd(ListNode head, int n) {
        if (n == 0 || head == null){
            return head;
        }

        ListNode firstNextLn = head;
        ListNode secondNextLn = new ListNode(0);
        int pos = 1;

        while (firstNextLn != null){
            if (pos == n){
                secondNextLn.next = head;
            }else if (pos > n){
                secondNextLn = secondNextLn.next;
            }

            firstNextLn = firstNextLn.next;
            pos++;
        }

        if (secondNextLn != null){
            if (secondNextLn.next.equals(head)){
                return head.next;
            }else{
                secondNextLn.next = secondNextLn.next.next;
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
        long startTime = System.nanoTime();
        ListNode l2 = removeNthFromEnd(l1, 5);
        long endTime = System.nanoTime();
        long time = endTime - startTime;

        System.out.println("removeNthFromEnd:" + ListNodeToString(l2) + " time:" + time);
    }

}
