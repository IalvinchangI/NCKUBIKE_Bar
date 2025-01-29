package indi.IalvinchangI.nckubikebar.utils;

import java.util.ArrayList;


/**
 * the column of DataFrame
 * 
 * @author IalvinchangI
 */
public class Series extends ArrayList<Integer> {
    
    /**
     * return a Series of zeros, with a size equal to size
     * 
     * @param size the length of Series
     * @return Series of zeros
     */
    public static Series zeros(int size) {
        Series out = new Series();

        for (int i = 0; i < size; i++) {
            out.add(0);
        }

        return out;
    }

    /**
     * sum the value in series
     * 
     * @return the sum value of series
     */
    public int sum() {
        int sum = 0;
        for(int i = 0; i < this.size(); i++) {
            sum += this.get(i);
        }
        return sum;
    }
}
