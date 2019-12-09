package com.csci430.website.function;

import org.mindrot.jbcrypt.BCrypt;

public class SlowHash {
    public static String generateHashedString(String str) {
        return BCrypt.hashpw(str, BCrypt.gensalt());
    }

    public static boolean verifyHashedString(String plain, String hashed) {
        return BCrypt.checkpw(plain, hashed);
    }
}
