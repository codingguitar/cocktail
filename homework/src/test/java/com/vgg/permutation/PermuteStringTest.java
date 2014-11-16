package com.vgg.permutation;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class PermuteStringTest {

    static List<String> abc = new ArrayList<String>();
    static {
        abc.add("abc");
        abc.add("acb");
        abc.add("bac");
        abc.add("bca");
        abc.add("cab");
        abc.add("cba");
    }
    @Test
    public void testRecursePermute() throws Exception {
        PermuteString permuteString = new PermuteString();
        List<String> list = permuteString.permute("abc");
        Assert.assertTrue(abc.equals(list));
    }
}