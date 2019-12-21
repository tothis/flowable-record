package com.example.util;

/**
 * @author 李磊
 * @version V1.0
 * @time 2019/10/29 12:55
 * @description
 */
public class StringUtil {

    public static boolean isEmpty(String value) {
        return value == null || value.length() == 0;
    }

    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }

    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isNotBlank(String value) {
        return !isBlank(value);
    }
}