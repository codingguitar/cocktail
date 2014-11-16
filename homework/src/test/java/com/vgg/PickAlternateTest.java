package com.vgg;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class PickAlternateTest {
    @Test
    public void testgetLastStanding(){
        PickAlternate pick = new PickAlternate();
        int alive = pick.getLastStanding(10);
        Assert.assertTrue(alive == 5);

        alive = pick.getLastStanding(100);
        Assert.assertTrue(alive == 73);
    }

}