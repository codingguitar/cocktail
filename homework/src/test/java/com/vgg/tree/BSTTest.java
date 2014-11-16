package com.vgg.tree;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

public class BSTTest {
    BST tree;
    @Before
    public void setup(){
        tree = new BST();
        tree.add(7); tree.add(8); tree.add(1); tree.add(2); tree.add(5);
        tree.add(3); tree.add(4); tree.add(6); tree.add(10); tree.add(9);
    }

    @Test
    public void testTree(){

        StringBuilder traversed = new StringBuilder();
        tree.inOrder(tree.getRoot(), traversed);
        Assert.assertTrue(traversed.toString().trim().equals("1 2 3 4 5 6 7 8 9 10"));

        traversed.setLength(0);
        tree.preOrder(tree.getRoot(), traversed);
        Assert.assertTrue(traversed.toString().trim().equals("7 1 2 5 3 4 6 8 10 9"));

        traversed.setLength(0);
        tree.postOrder(tree.getRoot(), traversed);
        Assert.assertTrue(traversed.toString().trim().equals("4 3 6 5 2 1 9 10 8 7"));

        Assert.assertTrue(tree.findNode(5) == null ? false : true);
        Assert.assertFalse(tree.findNode(15) == null ? false : true);
    }

    @Test
    public void testfindKthLargest(){
        Assert.assertTrue(tree.findKthLargest(4) == 4);
        Assert.assertTrue(tree.findKthLargest(2) == 2);

        //check for empty tree
        BST empty = new BST();
        Assert.assertTrue(empty.findKthLargest(1) == -1);

        //check for k out of bounds
        Assert.assertTrue(tree.findKthLargest(20) == -1);
    }
}