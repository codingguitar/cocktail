package com.vgg.sets;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a set of size n find all subsets of size k
 */
public class SizeKSubset {

    public void findSubsets(List set, int k){
        List<Integer> subset = new ArrayList<Integer>();
        recursiveKsubset(set, k, 0, subset);
    }


    private void recursiveKsubset(List set, int k, int idx, List<Integer> subset) {
        if (subset.size() == k){
            System.out.println(subset.toString());
            return;
        }

        if (idx == set.size()) return;
        Integer x = (Integer)set.get(idx);

        //generate subsets with x fixed and rest of the elements of the array
        subset.add(x);
        recursiveKsubset(set, k, idx+1, subset);

        //generate subsets with x removed and rest of the elements of the array
        subset.remove(x);
        recursiveKsubset(set, k, idx + 1, subset);
    }

        private void iterativeKSubset(List set, int k) {
        List<Integer> list = new ArrayList<Integer>();
        List<Integer> sublist = new ArrayList<Integer>();
        for (int i=0; i<set.size(); i++){

        }
    }
}
