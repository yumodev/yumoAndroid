package com.yumodev.java.airthmetic.tree;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by yumo on 10/6/16.
 */
public class TestTree {

    public static BinaryTreeNode createTree(int[] dataList){

        if (dataList == null ||dataList.length == 0){
            return null;
        }

        BinaryTreeNode root = new BinaryTreeNode(dataList[0]);
        List<BinaryTreeNode> list = new ArrayList<BinaryTreeNode>();
        list.add(root);
        for (int i = 1; i < dataList.length; i++){
            BinaryTreeNode node  = new BinaryTreeNode(dataList[i]);
            list.add(node);
            BinaryTreeNode parentNode = list.get(0);
            if (parentNode.mLeftChild == null){
                parentNode.mLeftChild = node;
            }else if(parentNode.mRightChild == null){
                parentNode.mRightChild = node;
                list.remove(0);
            }
        }

        return root;

    }

    /**
     * 前序遍历递归实现
     * @param node
     */
    public static void preOrderTraverse(BinaryTreeNode node){
        if (node == null){
            return;
        }

        System.out.println(node.toString());
        preOrderTraverse(node.mLeftChild);
        preOrderTraverse(node.mRightChild);
    }

    /**
     * 前序遍历非递归实现
     */
    public static void preOrder(BinaryTreeNode node){
        if (node == null){
            return;
        }

        Stack<BinaryTreeNode> stack = new Stack();
        while (stack.size() > 0 || node != null){
            while (node != null){
                node.toString();;
                stack.push(node);
                node = node.mLeftChild;
            }

            if (stack.size() > 0){
                node = stack.pop().mRightChild;
            }
        }

    }

    /**
     * 中序遍历-递归实现
     * @param node
     */
    public static void inOrderTraverse(BinaryTreeNode node){
        if (node == null){
            return;
        }

        inOrderTraverse(node.mLeftChild);
        node.toString();;
        inOrderTraverse(node.mRightChild);
    }

    /**
     * 中序非递归
     * @param node
     */
    public static void inOrder(BinaryTreeNode node){
        if (node == null){
            return;
        }

        Stack<BinaryTreeNode> stack = new Stack();

        while (stack.size() > 0 || node != null){
            while (node != null){
                stack.push(node);
                node = node.mLeftChild;
            }

            if (stack.size() > 0){
                node = stack.pop();
                node.toString();
                node = node.mRightChild;
            }
        }
    }


    /**
     * 后序遍历
     * @param node
     */
    public static void lastOrderTraverse(BinaryTreeNode node){
        if (node == null){
            return;
        }

        lastOrderTraverse(node.mLeftChild);
        lastOrderTraverse(node.mRightChild);
        node.toString();
    }

    public static void lastOrder(BinaryTreeNode node){
        if (node == null){
            return;
        }

        Stack<BinaryTreeNode> stack = new Stack();

        while (stack.size() >= 0 ){
            while (node.mLeftChild != null){
                stack.push(node);
                node = node.mLeftChild;
            }

            while (node != null && node.mRightChild == null){
                node.toString();

            }

            if (node.mRightChild == null){
                node.toString();;
            }else{
                stack.push(node);
            }
        }
    }

    /**
     * 层次遍历
     * @param node
     */
    public static void levelOrder(BinaryTreeNode node){
        if (node == null){
            return;
        }

        List<BinaryTreeNode> list = new ArrayList<>();
        list.add(node);
        while (list.size() >0){
            node = list.remove(0);
            node.toString();;
            if (node.mLeftChild != null){
                list.add(node.mLeftChild);
            }

            if (node.mRightChild != null){
                list.add(node.mRightChild);
            }

        }

    }


    public static void main(String[] args){
        int[] dataList = {1,2,3,4,5,6,7,8,9};
        BinaryTreeNode rootNote = createTree(dataList);
        preOrderTraverse(rootNote);
//        System.out.println();
//        preOrder(rootNote);


//        inOrderTraverse(rootNote);
//        System.out.println();
          //inOrder(rootNote);

//        lastOrderTraverse(rootNote);
//        System.out.println();
//        lastOrder(rootNote);

 //       levelOrder(rootNote);

    }
}
