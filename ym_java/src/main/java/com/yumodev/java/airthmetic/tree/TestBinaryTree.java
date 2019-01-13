package com.yumodev.java.airthmetic.tree;



import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by yumodev on 17/9/21.
 * 二叉树测试类
 */

public class TestBinaryTree extends TestCase {
    public BinaryTreeNode mRootNode;


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        System.out.println("setUp");
        int[] dataList = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        mRootNode = createTree(dataList);
    }

    public void testCreateTree() {
        int[] dataList = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        mRootNode = createTree(dataList);
    }

    /**
     * 构建一个二叉树
     *
     * @param dataList
     * @return
     */
    private BinaryTreeNode createTree(int[] dataList) {
        if (dataList == null || dataList.length == 0) {
            return null;
        }

        BinaryTreeNode root = new BinaryTreeNode(dataList[0]);
        List<BinaryTreeNode> list = new ArrayList<>();
        list.add(root);
        for (int i = 1; i < dataList.length; i++) {
            BinaryTreeNode node = new BinaryTreeNode(dataList[i]);
            list.add(node);
            BinaryTreeNode parentNode = list.get(0);
            if (parentNode.mLeftChild == null) {
                parentNode.mLeftChild = node;
            } else if (parentNode.mRightChild == null) {
                parentNode.mRightChild = node;
                list.remove(0);
            }
        }

        return root;
    }

    /**
     * 前序遍历 根，左，右
     */
    public void testPreOrder() {
        List<Integer> result = preOrderTraverse(mRootNode, null);
        System.out.println("前序遍历：" + result.toString());

        List<Integer> result1 = preOrder(mRootNode);
        System.out.println("前序遍历：" + result1.toString());
    }


    /**
     * 前序遍历递归实现
     *
     * @param rootNode
     * @return
     */
    private List<Integer> preOrderTraverse(BinaryTreeNode<Integer> rootNode, ArrayList<Integer> result) {
        if (result == null) {
            result = new ArrayList<>();
        }

        if (rootNode == null) {
            return result;
        }
        result.add(rootNode.getValue());
        preOrderTraverse(rootNode.mLeftChild, result);
        preOrderTraverse(rootNode.mRightChild, result);
        return result;
    }

    /**
     * 前序遍历非递归实现用栈来实现。
     * 通过列表，数组等等都可以实现。
     *
     * @param rootNode
     * @return
     */
    private List<Integer> preOrder(BinaryTreeNode<Integer> rootNode) {
        List<Integer> result = new LinkedList<>();
        Stack<BinaryTreeNode<Integer>> stack = new Stack<>();
        stack.push(rootNode);
        while (!stack.isEmpty()) {
            BinaryTreeNode<Integer> node = stack.pop();
            result.add(node.getValue());

            if (node.mRightChild != null) {
                stack.push(node.mRightChild);
            }

            if (node.mLeftChild != null) {
                stack.push(node.mLeftChild);
            }
        }
        return result;
    }

    /**
     * 中序遍历 左，中，右
     */
    public void testMidOrder() {
        List<Integer> result = midOrderTraverse(mRootNode, null);
        System.out.println("中序遍历：" + result.toString());

        List<Integer> result1 = midOrder(mRootNode);
        System.out.println("中序遍历：" + result1.toString());
    }

    /**
     * 递归实现中序遍历。
     *
     * @param rootNode
     * @param result
     * @return
     */
    private List midOrderTraverse(BinaryTreeNode rootNode, List result) {
        if (result == null) {
            result = new ArrayList<>();
        }

        if (rootNode == null) {
            return result;
        }

        midOrderTraverse(rootNode.mLeftChild, result);
        result.add(rootNode.getValue());
        midOrderTraverse(rootNode.mRightChild, result);
        return result;
    }


    /**
     * 中序遍历非递归实现
     *
     * @param rootNode
     * @return
     */
    private List midOrder(BinaryTreeNode rootNode) {
        List result = new LinkedList<>();

        Stack<BinaryTreeNode> stack = new Stack();
        BinaryTreeNode node = rootNode;
        while (node != null || !stack.isEmpty()) {
            //遍历左节点存入栈中。
            while (node != null) {
                stack.push(node);
                node = node.mLeftChild;
            }

            if (!stack.isEmpty()) {
                node = stack.pop();
                result.add(node.getValue());
                node = node.mRightChild;
            }
        }

        return result;
    }

    /**
     * 后续序遍历 左，右，根
     */
    public void testLastOrder() {
        List<Integer> result = lastOrderTraverse(mRootNode, null);
        System.out.println("后序遍历：" + result.toString());

        List<Integer> result1 = lastOrder(mRootNode);
        System.out.println("后序遍历："+result1.toString());
    }

    /**
     * 递归实现后续遍历。
     * @param rootNode
     * @param result
     * @return
     */
    private List lastOrderTraverse(BinaryTreeNode rootNode, List result) {
        if (result == null) {
            result = new ArrayList<>();
        }

        if (rootNode == null) {
            return result;
        }

        lastOrderTraverse(rootNode.mLeftChild, result);
        lastOrderTraverse(rootNode.mRightChild, result);
        result.add(rootNode.getValue());

        return result;
    }

    /**
     * 后续遍历-非递归实现
     * @param rootNode
     * @return
     */
    private List lastOrder(BinaryTreeNode rootNode){
        List result = new LinkedList<>();
        if (rootNode == null){
            return result;
        }

        Stack<BinaryTreeNode> stack = new Stack<>();
        BinaryTreeNode temp = null;
        BinaryTreeNode node = rootNode;
        while (!stack.isEmpty() || node != null){
            while (node != null){
                stack.push(node);
                node = node.mLeftChild;
            }

            if (!stack.isEmpty()){
                node = stack.pop();
                if (node.mRightChild == null || node.mRightChild == temp){
                    temp = node;
                    result.add(node.getValue());
                    node = null;
                }else{
                    stack.push(node);
                    node = node.mRightChild;
                }
            }
        }

        return result;
    }

    /**
     * 按层遍历
     */
    public void testLevelOrder(){
        List result = levelOrder(mRootNode);
        System.out.println("层序遍历："+result.toString());
    }

    /**
     * 层序遍历
     * @param rootNode
     * @return
     */
    private List levelOrder(BinaryTreeNode rootNode){
        List result = new LinkedList();
        if (rootNode == null){
            return result;
        }

        Queue<BinaryTreeNode> queue = new LinkedList<>();
        queue.offer(rootNode);
        while (!queue.isEmpty()){
            BinaryTreeNode node = queue.poll();
            result.add(node.getValue());
            if (node.mLeftChild != null){
                queue.offer(node.mLeftChild);
            }

            if (node.mRightChild != null){
                queue.offer(node.mRightChild);
            }
        }

        return result;
    }

    /**
     * 求二叉树的结点个数
     */
    public void testCountNodes(){
        int result = getCountNodesTraverse(mRootNode);
        System.out.println("该二叉树的结点个数为："+result);

        int result1 = getCountNodes(mRootNode);
        System.out.println("该二叉树的结点个数为："+result1);
    }

    /**
     * 递归遍历获取结点的个数。
     * @param rootNode
     * @return
     */
    private int getCountNodesTraverse(BinaryTreeNode rootNode){
        if (rootNode == null){
            return 0;
        }

        int left = getCountNodesTraverse(rootNode.mLeftChild);
        int right = getCountNodesTraverse(rootNode.mRightChild);
        return left + right + 1;
    }

    /**
     * 获取结点个数，所有结点，所有的遍历操作和层序遍历都可以实现。
     * 下面采用层序遍历实现。
     * @param rootNode
     * @return
     */
    private int getCountNodes(BinaryTreeNode rootNode){
        if (rootNode == null){
            return 0;
        }

        Queue<BinaryTreeNode> queue = new LinkedList();
        int result = 0;
        queue.offer(rootNode);
        while (!queue.isEmpty()){
            BinaryTreeNode node = queue.poll();
            result++;
            if (node.mLeftChild != null){
                queue.offer(node.mLeftChild);
            }

            if (node.mRightChild != null){
                queue.offer(node.mRightChild);
            }
        }
        return result;
    }



    /**
     * 求二叉树的叶子结点个数
     */
    public void testLeafNodes(){
        int result = getLeafNodesTraverse(mRootNode);
        System.out.println("该二叉树的叶子结点个数："+result);

        int result1 = getLeafNodes(mRootNode);
        System.out.println("该二叉树的叶子结点个数："+result1);
    }

    private int getLeafNodesTraverse(BinaryTreeNode rootNode){
        if (rootNode == null){
            return 0;
        }

        if (rootNode.mLeftChild == null && rootNode.mRightChild == null){
            return 1;
        }

        return getLeafNodesTraverse(rootNode.mLeftChild) + getLeafNodesTraverse(rootNode.mRightChild);
    }

    /**
     * 非递归遍历获取二叉树的数量。使用队列的方式实现。
     * @param rootNode
     * @return
     */
    private int getLeafNodes(BinaryTreeNode rootNode){
        if (rootNode == null){
            return 0;
        }

        Queue<BinaryTreeNode> queue = new LinkedList();
        int result = 0;
        queue.offer(rootNode);
        while (!queue.isEmpty()){
            BinaryTreeNode node = queue.poll();
            if (node.mLeftChild != null){
                queue.offer(node.mLeftChild);
            }

            if (node.mRightChild != null){
                queue.offer(node.mRightChild);
            }

            if (node.mLeftChild == null && node.mRightChild == null){
                result++;
            }
        }
        return result;
    }


    /**
     * 求二叉树的深度
     */
    public void testTreeDepth(){
        int result = getTreeDepthReverse(mRootNode);
        System.out.println("该二叉树的深度："+result);

        int result1 = getTreeDepth(mRootNode);
        System.out.println("该二叉树的深度："+result1);
    }

    /**
     * 获取二叉树的深度，递归实现
     * @param rootNode
     */
    public int getTreeDepthReverse(BinaryTreeNode rootNode){
        return 0;
    }


    /**
     * 获取树的深度，非递归实现
     * @param rootNode
     */
    public int getTreeDepth(BinaryTreeNode rootNode){
        if (rootNode == null){
            return 0;
        }
        return 0;
    }

    /**
     * 求二叉树的最小深度
     */
    public void testTreeMinDepth(){

    }


    /**
     * 求二叉树的第K层的结点个数
     */
    public void testKLevelNodeNums(){

    }


    /**
     * 求二叉树的是否为完全二叉树
     */
    public void testIsCompleteTree(){

    }


    /**
     * 求二叉树是否为平衡二叉树
     */
    public void testBalanceTree(){

    }

    /**
     * 求二叉树是否为二叉查找树
     */
    public void testBST(){

    }

    /**
     * 翻转二叉树
     */
    public void testReverseTree(){

    }


    /**
     * 求二叉树中插入结点
     */
    public void testInsertNodes(){

    }

    /**
     * 判断两个二叉树是否完全相同
     */
    public void testIsTwoTreeSame(){

    }
}
