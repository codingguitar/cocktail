package com.vgg;

/**
 * TODO : WIP
 */
public class Parantheses {

    Parantheses(){

    }

    /**
     *
     * @param pairCount
     * print all possible combinations of the parantheses pairs along with depth
     * Ex ()()() => ()()(), (())(), ((())), ()(()), (()())
     *
     * Approach :
     *          1. Starting with one pair there is only set of parantheses. "()"
     *          2. With two pairs insert the extra opening and closing parantheses in appropriate places. This results in only 2 sets "()() and (())"
     *          3. With three pairs do the same as step 2 starting with step 2 output
     *          ..
     *          this pattern suggests a recursive solution
     */
    public void generateBalancedParantheses(int pairCount) {
        String combination = "";
        recursiveGenerator(pairCount, 0, combination);
    }

    private void recursiveGenerator(int open, int close, String combination) {
        if (open == 0 && close == 0){
            System.out.println(combination);
        }
        if (open > 0) {
            recursiveGenerator(open - 1, close + 1, combination + "(");
        }
        if (close > 0) {
            recursiveGenerator(open, close - 1, combination + ")");
        }
    }
}
