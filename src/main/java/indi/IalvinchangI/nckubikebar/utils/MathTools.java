package indi.IalvinchangI.nckubikebar.utils;


/**
 * static math function
 * 
 * @author IalvinchangI
 */
public class MathTools {
    
    /**
     * return a ceil value of an int
     * 
     * @param value
     * @return value after ceiling
     */
    public static int ceil(int value) {
        int length = Integer.toString(value).length();
        int highestPlace = (int) Math.pow(10, length - 1);
        int highestPlaceValue = value / highestPlace;

        if (value % highestPlace == 0) {  // (+\d)(*0)
            return value;
        }
        else {
            return (highestPlaceValue + 1) * highestPlace;
        }
    }

    /**
     * return the sign of the value
     * 
     * @param value
     * @return -1, 0, 1
     */
    public static int getSign(int value) {
        if (value > 0) {
            return 1;
        }
        if (value < 0) {
            return -1;
        }
        return 0;
    }
}
