package com.yumodev.java.airthmetic.tree;

/**
 * Created by yumodev on 10/6/16.
 * 一个二叉树的定义
 */
public class BinaryTreeNode<T>{
    private T mValue;
    public BinaryTreeNode mLeftChild = null;
    public BinaryTreeNode mRightChild = null;

    public BinaryTreeNode(T value){
        mValue = value;
    }

    public T getValue(){
        return mValue;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("value:" + mValue);
        if (mLeftChild != null) {
            sb.append(" left:" + mLeftChild.getValue());
        }

        if (mRightChild != null) {
            sb.append(" right:" + mRightChild.getValue());
        }
        return sb.toString();
    }
}
