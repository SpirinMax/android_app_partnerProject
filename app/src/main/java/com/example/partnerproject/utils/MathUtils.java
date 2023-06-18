package com.example.partnerproject.utils;

public class MathUtils {
    public static String getIntegerValue(float value) {
        if (value % 10 == 0) {
            int intPrice = (int)(value);
            return String.valueOf(intPrice);
        } else {
            return String.valueOf(value);
        }
    }

    public static String roundValueAndGetString (float value){
        return String.valueOf(getIntegerValue(value));
    }
}
