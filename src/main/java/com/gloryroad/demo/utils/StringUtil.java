package com.gloryroad.demo.utils;

public class StringUtil {
    public static boolean isNotBlank(String value){
        if(value == null || value.trim().length() <= 0){
            return false;
        }
        return true;
    }

    public static boolean isBlank(String value){
        if(value == null || value.trim().length() <= 0){
            return true;
        }
        return false;
    }
}
