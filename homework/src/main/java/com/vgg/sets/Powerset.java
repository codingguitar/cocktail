package com.vgg.sets;

import java.util.List;

/**
 *
 */
public class Powerset {

    public void powerSet(List<Integer> set){

        //use k subset to generate power set        
        SizeKSubset subsetGenerator = new SizeKSubset();
        for (int k=0; k<=set.size(); k++){
            subsetGenerator.findSubsets(set, k);
        }
    }
}
