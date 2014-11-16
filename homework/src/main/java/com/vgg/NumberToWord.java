package com.vgg;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class NumberToWord {

    NumberToWord(){}

    public static String convertToWord(String number) {
        String word = UNKNOWN;
        int tripletIndex = 0;

        for (int i=number.length(); i>0; i-=3) {
            int start = i - 3 <=0 ? 0 : i-3;
            int end = i;
            if (word.equals(UNKNOWN)){
                word = convertTriplet(number.substring(start, end));
            }
            else {
                word = convertTriplet(number.substring(start,end)) +
                       " " +
                       getValueOrDefault(higherWord, String.valueOf(tripletIndex)) + ", " + word;
            }
            tripletIndex++;
        }
        return word.trim();
    }

    private static String convertTriplet(String triplet){

        //remove leading 0 if any
        triplet = String.valueOf(Integer.parseInt(triplet));
        char[] digits = triplet.toCharArray();
        String word = UNKNOWN;


        if (digits.length == 1){
            word = getValueOrDefault(unitWord, triplet);
        }
        else {
            String last2Digits;
            if (digits.length == 2){
                last2Digits = triplet;
            }
            else {
                //get the last 2 digits and check if they are < 20 and convert accordingly
                last2Digits = triplet.substring(1, 3);
            }

            if (Integer.parseInt(last2Digits) < 20){
                word = getValueOrDefault(tensWord, last2Digits);
            }
            else {
                word = getValueOrDefault(tensWord, last2Digits.substring(0, 1)) +
                       ' ' +
                       getValueOrDefault(unitWord, last2Digits.substring(1, 2));
            }

            if (digits.length > 2){
                word = getValueOrDefault(unitWord, triplet.substring(0, 1)) + " hundred " + word;
            }
        }
        return word;
    }

    /*Helper Methods*/
    private static String getValueOrDefault(Map<String, String> hash, String key){
        return hash.containsKey(key) ? hash.get(key) : UNKNOWN;
    }

    private static String UNKNOWN = "UNKNOWN";


    private static Map<String, String> unitWord = new HashMap<String, String>();
    private static Map<String, String> tensWord = new HashMap<String, String>();
    private static Map<String, String> higherWord = new HashMap<String, String>();
    static {
        unitWord.put("0", "");
        unitWord.put("1", "one");
        unitWord.put("2", "two");
        unitWord.put("3", "three");
        unitWord.put("4", "four");
        unitWord.put("5", "five");
        unitWord.put("6", "six");
        unitWord.put("7", "seven");
        unitWord.put("8", "eight");
        unitWord.put("9", "nine");
    }
    static {
        tensWord.put("11","eleven");
        tensWord.put("12","twelve");
        tensWord.put("13","thirteen");
        tensWord.put("14","fourteen");
        tensWord.put("15","fifteen");
        tensWord.put("16","sixteen");
        tensWord.put("17","seventeen");
        tensWord.put("18","eighteen");
        tensWord.put("19","nineteen");
        tensWord.put("2","twenty");
        tensWord.put("3","thirty");
        tensWord.put("4","fourty");
        tensWord.put("5","fifty");
        tensWord.put("6","sixty");
        tensWord.put("7","seventy");
        tensWord.put("8","eighty");
        tensWord.put("9","ninety");
    }
    static {
        higherWord.put("0", "");
        higherWord.put("1", "thousand");
        higherWord.put("2", "million");
        higherWord.put("3", "billion");
        higherWord.put("4", "trillion");
    }
}
