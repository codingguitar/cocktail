package com.vgg.array;

import java.util.Arrays;

/**
 Given a sorted array with some duplicate elements, move the additional duplicates to
 the end of the array. The unique elements of the output array should be on the left and sorted,
 the duplicates should be on the right in any order (the elements between curly braces in the example below):
 Example: input=1,2,2,2,3,3,4,5,5 output=1,2,3,4,5,{2,2,3,5}
 Example: input=1,2,2,3,4 output=1,2,3,4,{2}
 */
public class ReorderDuplicates {

    public String reorder(int[] arr){
        int currElement, placingIndex, finderIndex;
        String str = Arrays.toString(arr);

        if (arr.length <= 2) return str;

        currElement = arr[0];
        placingIndex = 0;
        finderIndex = 1;
        System.out.println(Arrays.toString(arr));
        while (finderIndex < arr.length){
            if (currElement != arr[finderIndex]){
                placingIndex++;
                int temp = arr[placingIndex];
                arr[placingIndex] = arr[finderIndex];
                arr[finderIndex] = temp;

                currElement = arr[placingIndex];
            }
            finderIndex++;
        }
        str = Arrays.toString(arr);
        System.out.println(str);
        return str;
    }
}
