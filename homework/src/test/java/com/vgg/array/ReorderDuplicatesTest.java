package com.vgg.array;

import org.junit.Assert;

public class ReorderDuplicatesTest {

    @org.junit.Test
    public void testReorder() throws Exception {
        int[] arr = new int[9];
        arr[0]=1;arr[1]=2;arr[2]=2;arr[3]=2;arr[4]=3;arr[5]=3;arr[6]=4;arr[7]=5;arr[8]=5;

        ReorderDuplicates sortedDups = new ReorderDuplicates();
        Assert.assertTrue(sortedDups.reorder(arr).equals("[1, 2, 3, 4, 5, 3, 2, 2, 5]"));
    }
}