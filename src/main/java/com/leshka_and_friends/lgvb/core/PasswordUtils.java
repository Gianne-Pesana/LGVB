/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.core;

import com.leshka_and_friends.lgvb.core.AppConfig;
import org.mindrot.jbcrypt.BCrypt;

public final class PasswordUtils {
    private static final int WORK_FACTOR = AppConfig.getInt("security.password.hash.strength"); 

    private PasswordUtils() {}

    public static String hashPassword(char[] password) {
        String plain = new String(password);
        try {
            return BCrypt.hashpw(plain, BCrypt.gensalt(WORK_FACTOR));
        } finally {
            // clear sensitive data
            java.util.Arrays.fill(password, '\0');
        }
    }

    public static boolean verifyPassword(char[] candidate, String storedHash) {
        if (candidate == null || storedHash == null) return false;
        String plain = new String(candidate);
        try {
            return BCrypt.checkpw(plain, storedHash);
        } finally {
            java.util.Arrays.fill(candidate, '\0');
        }
    }
}
