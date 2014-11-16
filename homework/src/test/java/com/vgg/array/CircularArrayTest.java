package com.vgg.array;

import junit.framework.Assert;
import org.junit.Test;

import javax.print.attribute.IntegerSyntax;

import static org.junit.Assert.*;

public class CircularArrayTest {

    private CircularArray<Integer> intBuffer = new CircularArray<Integer>(5);
    private CircularArray<String> strBuffer = new CircularArray<String>(5);

    @Test
    public void testIntBuffer() throws Exception {

        intBuffer.addToFront(4);
        intBuffer.addToFront(3);
        intBuffer.addToFront(2);
        intBuffer.addToFront(1);
        intBuffer.addToBack(5);
        intBuffer.addToBack(6);
        intBuffer.addToBack(7);
        intBuffer.addToFront(0);

        intBuffer.removeFront();
        intBuffer.removeBack();

        intBuffer.removeBack();
        intBuffer.removeBack();
        intBuffer.removeBack();

        Integer x = intBuffer.get(0);
        intBuffer.addToFront(1);
        intBuffer.set(2, 0);
        x = intBuffer.get(0);

        Assert.assertTrue(x.intValue() == 2);
    }

    @Test
    public void testStrBuffer() throws Exception {
        strBuffer.addToFront("one");
        strBuffer.addToFront("two");
        strBuffer.addToFront("three");

        String x = strBuffer.get(3);
        x = strBuffer.get(2);
        Assert.assertTrue(x.equals("one"));
    }
}