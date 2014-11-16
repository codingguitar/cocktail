package com.vgg.sets;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SetTests {
    List<Integer> list  = new ArrayList<Integer>();

    @Before
    public void setup(){
        list.add(1);list.add(2);list.add(3);list.add(4);list.add(5);
    }

    @Test
    public void testFindSubsets() throws Exception {
        SizeKSubset set = new SizeKSubset();
        set.findSubsets(list, 0);
    }

    @Test
    public void testPowerSet() throws Exception{
        Powerset powerset = new Powerset();
        powerset.powerSet(list);
    }

}