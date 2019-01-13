package com.yumodev.java.airthmetic.offer;

import java.util.*;

/**
 * Created by yumodev on 16/10/14.
 */
public class TestTree {

    public static void main(String[] args){
        //new ConstructBinaryTree().testConstructBinaryTree();
        //new MirrorOfBinaryTree().testMirrorOfBinaryTree();
        //new PrintLayer().testLayer();
        //new VerifySquenceOfBST().testVerifySquenceOfBST();
        //new FindPath().testFindPath();
        //new BinaryToList().testBinaryToList();
        //new TreeDepth().testTreeDepth();

        //System.out.println(isBalanced(initTree(new int[]{1,2,3,4,5,6,7,8})));

        //new Symmetrical().testSymmetrical();
        //new PrintLayerTree().testPrintLayerTree();
        //new PrintZigzag().testZigzag();
        //new SerializeTree().testSerivalizeTree();
        new KthNode().testKthNode();
    }

    public static class TreeNode{
        int mValue;
        TreeNode mLeft = null;
        TreeNode mRight = null;
        TreeNode mParent = null;

        public TreeNode(int value){
            mValue = value;
        }

        public TreeNode(){

        }
    }


    public static void printMidTree(TreeNode root){
        if (root != null){
            if (root != null){
                printMidTree(root.mLeft);
                System.out.println(root.mValue);
                printMidTree(root.mRight);
            }
        }
    }

    public static void printLayerTree(TreeNode root){
        if (root == null){
            throw new IllegalArgumentException("args should not be null");
        }

        Queue<TreeNode> queue = new ArrayDeque<TreeNode>();
        queue.add(root);

        while (!queue.isEmpty()){
            TreeNode node = queue.poll();
            System.out.println(node.mValue+" ");
            if (node.mLeft != null){
                queue.add(node.mLeft);
            }

            if (node.mRight != null){
                queue.add(node.mRight);
            }
        }
    }

    public static TreeNode initTree(int[] dataList){
        if (dataList == null ||dataList.length == 0){
            return null;
        }

        TreeNode root = new TreeNode(dataList[0]);
        List<TreeNode> list = new ArrayList<>();
        list.add(root);
        for (int i = 1; i < dataList.length; i++){
            TreeNode node  = new TreeNode(dataList[i]);
            list.add(node);
            TreeNode parentNode = list.get(0);
            if (parentNode.mLeft == null){
                parentNode.mLeft = node;
            }else if(parentNode.mRight == null){
                parentNode.mRight = node;
                list.remove(0);
            }
        }

        return root;

    }

    public static int getTreeDepth(TreeNode root){
        if (root == null){
            return 0;
        }

        int left = getTreeDepth(root.mLeft);
        int right = getTreeDepth(root.mRight);
        System.out.println("value:"+ root.mValue + " left:"+left +"   right:"+right);
        return left > right ? left + 1 : right + 1;
    }

    public static boolean isBalanced(TreeNode node){
        if (node == null){
            return true;
        }

        int left = getTreeDepth(node.mLeft);
        int right = getTreeDepth(node.mRight);

        if (left - right > 1 || left - right < -1){
            return false;
        }

        return isBalanced(node.mLeft) && isBalanced(node.mRight);
    }

    /*****************************************
     剑指Offer6 重建二叉树
     题目:输入某二叉树的前序遍历和中序遍历的结果,请重建出该二叉树.
     假设输入的前序遍历和中序遍历的结果中都不含重复的数字.
     例如输入谦虚遍历序列{1,2,4,7,3,5,6,8}和中序遍历序列{4,7,2,1,5,3,8,6}
     输入如下的二叉树
            1
         2    3
     4      5      6
        7        8
     *****************************************/
    public static class ConstructBinaryTree{
        public void testConstructBinaryTree(){
            int[] preOrder = {1,2,4,7,3,5,6,8};
            int[] inOrder = {4,7,2,1,5,3,8,6};
            TreeNode node = constructBinaryTree(preOrder, 0, preOrder.length -1, inOrder, 0, inOrder.length -1);
            printMidTree(node);
        }

        public TreeNode constructBinaryTree(int[] preOrder, int ps, int pe, int[] inOrder, int is, int ie){
            if (preOrder == null || inOrder == null){
                throw new IllegalArgumentException("arg should not null");
            }

            if (ps < pe){
                return null;
            }

            int rootValue = preOrder[0];
            int index = is;
            while (index <= ie && inOrder[index] != rootValue){
                index++;
            }

            if (index > ie){
                throw new IllegalArgumentException("invalid input");
            }

            TreeNode node = new TreeNode(rootValue);

            node.mLeft = constructBinaryTree(preOrder, ps + 1, ps + index -is, inOrder, is, index-1);
            node.mRight = constructBinaryTree(preOrder, ps + index - is + 1, pe, inOrder, index + 1, ie);
            return node;
        }
    }


    /*****************************************
     剑指Offer18 树的子结构
     题目:输入两课二叉树A和，判断B是不是A的子结构.
     二叉树节点的定义如下:
     *****************************************/

    public static class SubTree{

        public void testSubTree(){
            TreeNode tree = initTree(new int[]{1,2,3,4,5,6,7,8});
            TreeNode sub = initTree(new int[]{1,2,3});
            System.out.println(subTree(tree, sub));
        }

        public boolean subTree(TreeNode tree, TreeNode sub){
            return false;
        }
    }



    /*****************************************
     剑指Offer19 树的镜像
     题目:输入一个二叉树,该函数输出它的镜像
     *****************************************/
    public static class MirrorOfBinaryTree{

        public void testMirrorOfBinaryTree(){
            TreeNode tree = initTree(new int[]{1,2,3,4,5,6,7,8});
            mirrorOfBinaryTree(tree);
            printMidTree(tree);
        }

        public void mirrorOfBinaryTree(TreeNode treeNode){
            if (treeNode == null){
                return;
            }

            if (treeNode.mLeft == null && treeNode.mRight == null){
                return;
            }

            TreeNode temp  = treeNode.mLeft;
            treeNode.mLeft = treeNode.mRight;
            treeNode.mRight = temp;


            if (treeNode.mLeft != null){
                mirrorOfBinaryTree(treeNode.mLeft);
            }

            if (treeNode.mRight != null){
                mirrorOfBinaryTree(treeNode.mRight);
            }

        }
    }

    /*****************************************
     剑指Offer23 从上往下打印二叉树
     题目:从上往下打印出二叉树的每个结点.同一层的结点按照从左到右的顺序打印.
     *****************************************/
    public static class PrintLayer{
        public void testLayer(){
            printLayerTree(initTree(new int[]{1,2,3,4,5,6,7,8}));
        }


    }

    /*****************************************
     剑指Offer24 二叉搜索树的后序遍历序列.
     题目:输入一个整形数组,判断该数组是不是某二叉树索索数的后续遍历的结果.
     假设输入的任意数字各不相同.
     *****************************************/

    public static class VerifySquenceOfBST{
        public void testVerifySquenceOfBST(){
            int[] arr1 = {5,7,6,9,11,10,8};
            int[] arr2 = {7,4,6,5};

            System.out.println(verifySquenceOfBST(arr1, 0, arr1.length-1));
            System.out.println(verifySquenceOfBST(arr2, 0, arr2.length-1));
        }

        private boolean verifySquenceOfBST(int sequence[], int lo, int hi){
            if (sequence == null || lo > hi){
                return false;
            }

            int root = sequence[hi];
            int leftIndex = 0;
            for (; leftIndex < hi; leftIndex++){
                if (sequence[leftIndex] > root){
                    break;
                }
            }

            int rightIndex = leftIndex;
            for(; rightIndex < hi; rightIndex++){
                if (sequence[rightIndex] < root){
                    return false;
                }
            }

            boolean left = true;
            if (leftIndex > 0){
                left = verifySquenceOfBST(sequence, 0, leftIndex-1);
            }

            if (!left){
                return left;
            }

            boolean right = true;
            if (leftIndex < hi){
                right = verifySquenceOfBST(sequence, leftIndex, hi -1);
            }

            return  right;
        }

    }

    /*****************************************
     剑指Offer25 二叉树中和为某一值的路径.
     题目:输入一个二叉树和整数,打印出二叉树中结点值的和为
     输入整数的所有路径.从树的根节点开始往下一只到叶节点
     经过的节点形成的一条路径.
     *****************************************/

    public static class FindPath{

        public void testFindPath(){
            TreeNode root = initTree(new int[]{1,2,3,4,5,6,7,8});
            findPath(root, 15);

        }

        private void findPath(TreeNode root, int expectedSum){
            List<Integer> result = new ArrayList<>();
            if (root != null){
                findPath(root, 0, expectedSum, result);
            }
        }

        private void findPath(TreeNode root, int curSum, int expectedSum, List<Integer> result){
            if (root == null){
                return;
            }

            curSum += root.mValue;
            result.add(root.mValue);

            if (curSum < expectedSum){

                findPath(root.mLeft, curSum, expectedSum, result);
                findPath(root.mRight, curSum, expectedSum, result);

            }else if (curSum == expectedSum){
                if (root.mLeft == null && root.mRight == null){
                    System.out.println(result.toString());
                }
            }

            result.remove(result.size() -1);

        }
    }

    /*****************************************
     剑指Offer27 二叉搜索树与双向链表
     题目:输入一颗二叉搜索树,
     将该二叉搜索树转换成一个排序的双向链表.要求不能创建任何新的结点,
     只能调整树中结点指针的指向.
     *****************************************/
    public static class BinaryToList{

        public void testBinaryToList(){
            TreeNode root = initTree(new int[]{10,6,14,4,8,12,16});
            TreeNode head = binaryToList(root);

            while (head != null){
                System.out.println(head.mValue);
                head = head.mRight;
            }
        }

        public TreeNode binaryToList(TreeNode root){
            TreeNode[] listNode = new TreeNode[1];
            binaryToList(root, listNode);

            TreeNode head = listNode[0];
            while (head.mLeft != null){
                head = head.mLeft;
            }

            return head;

        }

        public void binaryToList(TreeNode node, TreeNode[] listNode){
            if (node == null){
                return;
            }

            if (node.mLeft != null){
                binaryToList(node.mLeft, listNode);
            }

            node.mLeft = listNode[0];
            if (listNode[0] != null){
                listNode[0].mRight = node;
            }

            listNode[0] = node;

            if (node.mRight != null){
                binaryToList(node.mRight, listNode);
            }
        }
    }

    /*****************************************
     剑指Offer39 二叉树的深度
     题目:输入一颗二叉树的根节点,求该树的深度.
     从根结点到叶结点一次经过的结点,形成树的一条路径.最长路径的长度是树的深度.
     *****************************************/
    public static class TreeDepth{

        public void testTreeDepth(){
            System.out.println(getTreeDepth(initTree(new int[]{1,2,3,4,5,6,7,8})));
        }
    }


    /*****************************************
     剑指Offer59 对称二叉树
     题目:请实现一个函数,用来判断一颗二叉树是不是对称的
     如果一颗二叉树和它的镜像一样,那么它是对称的.
     *****************************************/
     public static class Symmetrical{
        public void testSymmetrical(){
            TreeNode node1 = initTree(new int[]{8,6,6,5,7,7,5});
            TreeNode node2 = initTree(new int[]{8,6,6,5,7,7,6});

            System.out.println(isSymmetrical(node1, node2));
        }

        public boolean isSymmetrical(TreeNode root1, TreeNode root2){

            if (root1 == null && root2 == null){
                return true;
            }

            if (root1 == null || root2 == null){
                return false;
            }

            if (root1.mValue != root2.mValue){
                return false;
            }

            return isSymmetrical(root1.mLeft, root2.mRight) && isSymmetrical(root1.mRight, root2.mLeft);
        }

    }


    /*****************************************
     剑指Offer60 把二叉树打印成多行
     题目:请上从小打印二叉树.同一层的结点按从左到右的顺序打印,没一层打印到一行.
     *****************************************/
    public static class PrintLayerTree{
        public void testPrintLayerTree(){
            TreeNode node1 = initTree(new int[]{8,6,6,5,7,7,5});
            printLayerTree(node1);
        }

        public void printLayerTree(TreeNode root){
            if (root == null){
                throw new IllegalArgumentException("args should not be null");
            }

            Queue<TreeNode> queue = new ArrayDeque<>();
            queue.add(root);
            int nums = 0;
            int deNums = 1;
            while (!queue.isEmpty()){
                TreeNode node = queue.poll();
                if (node.mLeft != null){
                    queue.add(node.mLeft);
                    nums++;
                }

                if (node.mRight != null){
                    queue.add(node.mRight);
                    nums ++;
                }

                System.out.print(node.mValue + " ");
                deNums--;
                if (deNums == 0){
                    deNums = nums;
                    System.out.println();
                }
            }
        }

    }


    /*****************************************
     剑指Offer61  按照之字形顺序打印二叉树.
     题目:请实现一个函数按照之字形顺序打印二叉树,
     即第一行按照从左到右的顺序的打印,
     第二层按照从右到左的顺序打印,
     第三行在按照从左到右的顺序打印
     *****************************************/
    public static class PrintZigzag{
        public void testZigzag(){
            TreeNode node = initTree(new int[]{8,6,5,5,7,7,6});
            zigZag(node);
        }

        public void zigZag(TreeNode root){
            if (root == null){
                return;
            }

            Stack<TreeNode>[] stacks = new Stack[2];
            stacks[0] = new Stack<>();
            stacks[1] = new Stack<>();
            stacks[0].push(root);
            int current = 0;
            int next = 1;
            while (!stacks[0].isEmpty() || !stacks[1].isEmpty()){
                TreeNode node = stacks[current].pop();
                System.out.print(node.mValue+" ");

                if (current == 0){
                    if (node.mLeft != null) {
                        stacks[next].push(node.mLeft);
                    }

                    if (node.mRight != null){
                        stacks[next].push(node.mRight);
                    }
                }else {
                    if (node.mRight != null){
                        stacks[next].push(node.mRight);
                    }

                    if (node.mLeft != null){
                        stacks[next].push(node.mLeft);
                    }
                }

                if (stacks[current].isEmpty()){
                    current = 1 - current;
                    next = 1 - next;
                    System.out.println();
                }

            }



        }
    }

    /*****************************************
     剑指Offer62 序列化二叉树
     题目:请实现两个函数,分别实现二叉树的序列化和反序列化.
     *****************************************/
     public static class SerializeTree{
        public void testSerivalizeTree(){
            TreeNode root = initTree(new int[]{1,2,3,4,5,6,7,8});
            List<Integer> list = new ArrayList<>();
            serialize(root, list);

            TreeNode node = Deserialize(list, 0);
            printLayerTree(node);
        }

        public void serialize(TreeNode root, List<Integer> list){
            if (root == null){
                return;
            }

            ArrayList<TreeNode> queue = new ArrayList<TreeNode>();
            queue.add(root);

            while (!queue.isEmpty()){
                TreeNode node = queue.remove(0);
                if (node == null){
                    list.add(null);
                    continue;
                }

                list.add(node.mValue);
                queue.add(node.mLeft);
                queue.add(node.mRight);
            }
        }


        public TreeNode Deserialize(List<Integer> list, int idx){
            if (list.size() < 1 || idx < 0 || list.size() <= idx || list.get(idx) == null){
                return null;
            }

            TreeNode node = new TreeNode(list.get(idx));
            node.mLeft = Deserialize(list, idx * 2 + 1);
            node.mRight= Deserialize(list, idx * 2 + 2);
            return node;
        }
    }

    /*****************************************
     剑指Offer63 二叉搜索树的第K个节点.
     题目:给定一颗二叉树,请找出其中的第K大的结点.
     *****************************************/
    public static class KthNode{
        public void testKthNode(){
            TreeNode tree = initTree(new int[]{5,3,7,2,4,6,8});
            int[] k = new int[1];
            k[0] = 4;

            TreeNode node = kehNode(tree, k);
            System.out.println(node.mValue);
        }

        public TreeNode kehNode(TreeNode root, int[]k){
            TreeNode result = null;
            if (root.mLeft != null){
                result = kehNode(root.mLeft, k);
            }

            if (result == null){
                if (k[0] == 1){
                    result = root;
                }else{
                    k[0] --;
                }
            }

            if (result == null && root.mRight != null){
                result = kehNode(root.mRight, k);
            }

            return result;
        }
    }



}
