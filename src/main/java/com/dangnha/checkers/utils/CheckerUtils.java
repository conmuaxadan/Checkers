package com.dangnha.checkers.utils;

public class CheckerUtils {
    /**
     * Check if two checkTypes are opponents
     * @param str1 checkType 1
     * @param str2 checkType 2
     * @return true if they are opponents, false if not. Example: str1 = "KB", str2 = "W" => true
     */
    public static boolean isOpponent(String str1, String str2) {
        System.out.println(str1 + " " + str2);
        if (str1.startsWith("K") && str2.startsWith("K")) {
            return str1.charAt(1) != str2.charAt(1);
        } else {
            char colorType1 = str1.charAt(str1.length() - 1);
            char colorType2 = str2.charAt(str2.length() - 1);
            return colorType1 != colorType2;
        }
    }


}
