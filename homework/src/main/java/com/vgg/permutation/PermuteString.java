package com.vgg.permutation;

import java.util.ArrayList;

/**
 *
 */
public class PermuteString {

    public ArrayList<String> permute(String str){
        ArrayList<String> list = new ArrayList<String>();
        recursePermute("", str, list);
        return list;
    }

    private void recursePermute(String prefix, String str, ArrayList list){
        if (str.length() == 0){
            System.out.println(prefix);
            list.add(prefix);
        }
        else {
            for (int i=0; i<str.length(); i++){
                recursePermute(prefix + str.charAt(i) , str.substring(0, i) + str.substring(i+1, str.length()), list);
            }
        }
    }
}
