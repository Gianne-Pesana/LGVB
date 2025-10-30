package com.leshka_and_friends.lgvb.utils;

public class StringUtils {

    private StringUtils() {
        // Private constructor to prevent instantiation
    }

    public static String toProperCase(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        StringBuilder converted = new StringBuilder();
        boolean convertNextCharToUpper = true;
        for (char ch : text.toCharArray()) {
            if (Character.isSpaceChar(ch)) {
                convertNextCharToUpper = true;
                converted.append(ch);
            } else if (convertNextCharToUpper) {
                converted.append(Character.toUpperCase(ch));
                convertNextCharToUpper = false;
            } else {
                converted.append(Character.toLowerCase(ch));
            }
        }
        return converted.toString();
    }
}
