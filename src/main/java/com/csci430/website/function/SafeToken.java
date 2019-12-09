package com.csci430.website.function;

import java.security.SecureRandom;
import java.util.Base64;

public class SafeToken {
    public static String generateSafeToken(int length) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
        return encoder.encodeToString(bytes);
    }
}
