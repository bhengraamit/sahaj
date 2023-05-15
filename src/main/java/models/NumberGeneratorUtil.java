package models;

public class NumberGeneratorUtil {

    public static int getIncrementedNumber(int value){
        value = (value + 1) % Integer.MAX_VALUE; // to avoid the overflow of int
        return value;
    }
}
