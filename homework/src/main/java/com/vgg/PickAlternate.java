package com.vgg;

/**
 Given a set of people standing in line where the person starting with
 1 kills the next person with a sword and passes the sword to the one after, going
 in circles until there is one last standing person, pick the one who survives
 */
public class PickAlternate {
    PickAlternate(){}

    public int getLastStanding(int total){

        int alive = total;
        int start=1, current = 1;
        int prevMultiplier=1;
        int index=1;
        int lastStanding=start;
        int multiplier = (int)Math.pow(2,index);
        while (alive > 1){
            if (current + multiplier > total){
                if (current + prevMultiplier > total) {
                    start = start + multiplier;
                    lastStanding = start;
                }
                current = start;
                prevMultiplier = multiplier;
                index++;
                alive--;
                multiplier = (int)Math.pow(2,index);
            }
            else {
                alive--;
                current = current + multiplier;
            }
        }
        return lastStanding;
    }
}
