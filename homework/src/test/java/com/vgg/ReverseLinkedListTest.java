package com.vgg;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReverseLinkedListTest {

    @Test
    public void testGetList() throws Exception {
        ReverseLinkedList list = new ReverseLinkedList();
        list.addNode(1);list.addNode(10);list.addNode(14);list.addNode(2);list.addNode(7);
        list.addNode(5);list.addNode(13);list.addNode(17);list.addNode(3);list.addNode(8);

        Assert.assertTrue(list.getList().equals("1 10 14 2 7 5 13 17 3 8"));

        list.reverse();
        Assert.assertTrue(list.getList().equals("8 3 17 13 5 7 2 14 10 1"));

        list.addNode(100);
        Assert.assertTrue(list.getList().equals("8 3 17 13 5 7 2 14 10 1 100"));
    }
}