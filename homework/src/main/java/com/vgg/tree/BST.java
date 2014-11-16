package com.vgg.tree;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Return values in recursion can be accomplished by using mutable parameters.
 * recurseKthLargest demonstrates that and the wrapper function findKthLargest wraps the recursive
 * function to make the interface look cleaner for public use.
 */
public class BST {

    private Node root = null;
    private String traversed;

    BST(){}

    public void add(int val){
        Node current = new Node(val);
        if (root == null){
            root = current;
        }
        else {
            Node parent = findParent(val);
            if (val < parent.getVal()){
                parent.left = current;
            }
            else {
                parent.right = current;
            }
        }
    }

    public Node findNode (int val){
        Node current = root;
        boolean found = false;

        while (!found && current != null){
            if (current.getVal() == val){
                found = true;
            }
            else if (val < current.getVal()){
                    current = current.left;
            }
            else {
                current = current.right;
            }
        }
        return current;
    }

    private Node findParent(int val) {
        Node parent = root, current = root;
        boolean found = false;
        while (!found) {
            if (val < parent.getVal()){
                current = parent.left;
            }
            else {
                current = parent.right;
            }
            if (current == null){
                found = true;
            }
            else {
                parent = current;
            }
        }
        return parent;
    }

    public int findKthLargest(int k) {
        AtomicInteger mutableK = new AtomicInteger();
        mutableK.set(k);
        AtomicInteger val = new AtomicInteger(-1);
        if (root != null){
            recurseKthLargest(root, mutableK, val);
        }
        return val.get();
    }

    private void recurseKthLargest(Node node, AtomicInteger k, AtomicInteger val){
        if (node != null) {
            if (k.get() > 0) {
                recurseKthLargest(node.left, k, val);
            }
            k.decrementAndGet();
            if (k.get() == 0){
                val.set(node.val);
            }
            if (k.get() > 0) {
                recurseKthLargest(node.right, k, val);
            }
        }
    }

    public void inOrder(Node node, StringBuilder traversed){
        if (node != null) {
            inOrder(node.left, traversed);
            traversed.append(node.getVal() + " ");
            inOrder(node.right, traversed);
        }
    }

    public void preOrder(Node node, StringBuilder traversed) {
        if (node != null) {
            traversed.append(node.getVal() + " ");
            preOrder(node.left, traversed);
            preOrder(node.right, traversed);
        }
    }

    public void postOrder(Node node, StringBuilder traversed) {
        if (node != null) {
            postOrder(node.left, traversed);
            postOrder(node.right, traversed);
            traversed.append(node.getVal() + " ");
        }
    }

    public Node getRoot() {
        return root;
    }


    private class Node {
        private int val;
        private Node left;
        private Node right;

        Node(int val){
            this.val = val;
            this.left = null;
            this.right = null;
        }

        public int getVal(){
            return val;
        }
    }
}
