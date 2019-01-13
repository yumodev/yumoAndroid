/**
 * Tree.java
 * yumo
 * 2015-1-16
 * 二叉树类
 */
package com.yumodev.java.airthmetic.tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * yumodev
 */
public class Tree {

    public Tree() {}


    /**
     * yumodev
     * @param str void
     * 2015-1-16
     */
    public void CreateTree(String str) {
    }
    
    /**
     * 获取节点个数 递归实现
     * yumodev
     *
     * @param node
     * @return int
     * 2015-1-16
     */
    public int getCodeNumRec(TreeNode node) {
        if (node == null) return 0;

        return getCodeNumRec(node.lChild) + getCodeNumRec(node.rChild) + 1;

    }

    /**
     * 非递归遍历结点的个数。自己想了半天没有想出来，原来使用队列实现的，哈哈。大大的赞
     * yumodev
     * @param node
     * @return int
     * 2015-1-18
     */
    public int getCodeNum(TreeNode node) {
        if (node == null) return 0;

        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.add(node);
        int count = 1;
        while (queue.size() > 0) {
            TreeNode treeNode = queue.remove();

            if (treeNode.lChild != null) {
                queue.add(treeNode.lChild);
                count++;
            }

            if (treeNode.rChild != null) {
                queue.add(treeNode.rChild);
                count++;
            }
        }
        return count;
    }

    /**
     * 异步方法求一个二叉树的高度 递归实现了。
     * yumodev
     *
     * @param node
     * @return int
     * 2015-1-16
     */
    public int getDepthRec(TreeNode node) {
        if (node == null) return 0;

        int left = getDepth(node.lChild);

        int right = getDepth(node.rChild);

        return (left > right) ? left + 1 : right + 1;
    }

    /**
     * 获取树的高度，非递归方法
     * yumodev
     *
     * @param root
     * @return int
     * 2015-1-18
     */
    public int getDepth(TreeNode root) {
        if (root == null) return 0;

        int depth = 0;
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.add(root);
        int nNextNums = 0;
        int nCurrent = 1;

        while (!queue.isEmpty()) {
            nCurrent--;
            TreeNode node = queue.remove();
            if (node.lChild != null) {
                nNextNums++;
                queue.add(node.lChild);
            }

            if (node.rChild != null) {
                nNextNums++;
                queue.add(node.rChild);
            }

            if (nCurrent == 0) {
                depth++;
                nCurrent = nNextNums;
                nNextNums = 0;

            }
        }

        return depth;
    }

    /**
     * 前序递归遍历一个二叉树
     * yumo
     * @param root void
     * 2015-1-18
     */
    public void preOrderTraversalRec(TreeNode root) {
        if (root == null) return;
        System.out.print(root.value + " ");
        preOrderTraversalRec(root.lChild);
        preOrderTraversalRec(root.rChild);
    }

    public void preOrderTraversal(TreeNode root) {
        if (root == null) return;
        Stack<TreeNode> stack = new Stack<TreeNode>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();

            System.out.print(node.value + " ");

            if (node.rChild != null) stack.push(node.rChild);
            if (node.lChild != null) stack.push(node.lChild);
        }
    }

    //中序遍历
    public void inOrderTraversalRec(TreeNode root) {
        if (root == null) return;
        inOrderTraversalRec(root.lChild);
        System.out.print(root.value + " ");
        inOrderTraversalRec(root.rChild);
    }

    public void inOrderTraversal(TreeNode root) {
        if (root == null) return;
        Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode cur = root;
        while (true) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.lChild;
            }

            if (stack.isEmpty()) break;

            cur = stack.pop();
            System.out.print(cur.value + " ");

            //stack.push(pop.rChild);
            cur = cur.rChild;
        }
    }

    //后续遍历
    public void postOrderTraversalRec(TreeNode root) {
        if (root == null) return;

        postOrderTraversalRec(root.rChild);
        postOrderTraversalRec(root.lChild);

        System.out.print(root.value + " ");
    }

    public void postOrderTraversal(TreeNode root) {
        if (root == null) return;
        Stack<TreeNode> stack = new Stack<TreeNode>();

        TreeNode cur = root;
        while (true) {
            while (cur != null) {
                stack.push(cur.rChild);
                cur = cur.rChild;
            }


            TreeNode pop = stack.pop();
            System.out.println(pop.value + " ");

            TreeNode peek = stack.peek();
            cur = cur.lChild;
            stack.push(cur);
//			while(pop.lChild != null)
//			{
//				stack.push(pop.lChild);
//				pop = pop.lChild;
//			}
        }
    }

    //子节点的定义
    public class TreeNode {

        public int value;
        public char key;
        public TreeNode lChild = null;
        public TreeNode rChild = null;
        public TreeNode() {};

        public TreeNode(int value) {
            this.value = value;
            this.lChild = null;
            this.rChild = null;
        }

        public TreeNode(int value, TreeNode lChild, TreeNode rChild) {
            this.value = value;
            this.lChild = lChild;
            this.rChild = rChild;
        }


        public void SetNode(int value, TreeNode lChild, TreeNode rChild) {
            this.value = value;
            this.lChild = lChild;
            this.rChild = rChild;
        }

        public String toString() {
            return String.valueOf(value);
        }
    }

}
