package com.yumodev.java.airthmetic.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yumodev on 10/11/16.
 * 堆是一颗完全二叉树,其任何一非叶节点满足性质.
 * key[i] <= key[2i+1]  && key[i] <= key[2i+2]
 * 或者key[i] <= key[2i +1] && key[i] <= key[2i+2]
 */
public final class MaxHeap<T extends  Comparable<T>>{
    private List<T> items;
    private int cursor;

    public MaxHeap(){
        this(32);
    }

    public MaxHeap(int size){
        items = new ArrayList<>(size);
        cursor = -1;
    }

    public void siftUp(int index){
        if (index == 0){
            return;
        }

        T temp = items.get(index);
        int parentIndex;
        while (index > 0){

            parentIndex = (index -1) >> 1;
            if (items.get(parentIndex).compareTo(temp) < 0){
                items.set(index, items.get(parentIndex));
                index = parentIndex;
            }else{
                break;
            }
        }

        items.set(index, temp);

    }

    public void siftDown(int index){
        T temp = items.get(index);

        int left = 2 * index + 1;
        int right = left + 1;
        int max = left;
        T maxNode = null;

        while (left < items.size()){
            maxNode = items.get(left);
            max = left;

            right = left + 1;
            if (right < items.size()){
                if (maxNode.compareTo(items.get(right)) < 0){
                    maxNode = items.get(right);
                    max = left;
                }
            }

            if (maxNode.compareTo(temp) > 0){
                items.set(index, maxNode);
                index = max;
                left = index * 2 + 1;
            }else{
                break;
            }

        }

        items.set(index, temp);

    }

    public T deleteTop(){
        if (items.isEmpty()){
            return null;
        }

        T maxItem = items.get(0);
        T lastItem = items.remove(items.size() -1);

        if (items.isEmpty()){
            return lastItem;
        }

        items.set(0, lastItem);
        siftDown(0);
        return maxItem;
    }

    public void add(T item){
        items.add(item);
        siftUp(items.size() - 1);
    }

    public T next(){
        if (cursor >= items.size()){
            throw  new RuntimeException("No more element");
        }

        return items.get(cursor);
    }

    public boolean hasNext(){
        cursor++;
        return  cursor < items.size();
    }

    public T first(){
        if (items.size() == 0){
            return null;
        }

        return items.get(0);
    }

    public boolean isEmpty(){
        return items.isEmpty();
    }

    public int size(){
        return items.size();
    }

    public void clear(){
        items.clear();
        cursor = -1;
    }

    @Override
    public String toString() {
        return items.toString();
    }
}
