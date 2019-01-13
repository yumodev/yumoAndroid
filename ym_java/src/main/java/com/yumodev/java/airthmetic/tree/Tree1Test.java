/**
 * Tree1Test.java
 * yumo
 * 2015-1-16
 * TODO
 */
package com.yumodev.java.airthmetic.tree;

import com.yumodev.java.airthmetic.tree.Tree.TreeNode;

import java.util.HashMap;


/**
 * yumo
 */
public class Tree1Test {

    /**
     *
     */
    public Tree1Test() {
    }

    public void Test() {
        HashMap<String, String> map = new HashMap<String, String>();
        TestOne();
    }

    public void TestOne() {
        Tree tree = new Tree();

        TreeNode rootNode = tree.new TreeNode(5);
        TreeNode node1 = tree.new TreeNode(6);
        TreeNode node2 = tree.new TreeNode(4);
        TreeNode node3 = tree.new TreeNode(2);
        TreeNode node4 = tree.new TreeNode(7);
        TreeNode node5 = tree.new TreeNode(8);

        rootNode.lChild = node2;
        rootNode.rChild = node4;

        node4.lChild = node1;
        node4.rChild = node5;

        node2.lChild = node3;

        //输出所有的叶子节点个数
        System.out.println("递归方法：输出所有的叶子节点个数:" + tree.getCodeNumRec(rootNode));
        System.out.println("非递归方法：输出所有的叶子节点个数:" + tree.getCodeNum(rootNode));

        //输出树的宽带
        System.out.println("递归方法：树的宽度：:" + tree.getDepthRec(rootNode));
        System.out.println("非递归方法：树的宽度：:" + tree.getDepth(rootNode));

        //前序遍历
        System.out.println("递归方法：前序遍历：:");
        tree.preOrderTraversalRec(rootNode);
        System.out.println();

        System.out.println("非递归方法：前序遍历：:");
        tree.preOrderTraversal(rootNode);
        System.out.println();

        //中序遍历
        System.out.println("递归方法：中序遍历：:");
        tree.inOrderTraversalRec(rootNode);
        System.out.println();

        System.out.println("非递归方法：中序遍历：:");
        tree.inOrderTraversal(rootNode);
        System.out.println();

        //后序遍历
        System.out.println("递归方法：后序遍历：:");
        tree.postOrderTraversalRec(rootNode);
        System.out.println();

        System.out.println("非递归方法：后序遍历：:");
        tree.postOrderTraversal(rootNode);
        System.out.println();
    }

    public void TestTwo() {

    }

}
