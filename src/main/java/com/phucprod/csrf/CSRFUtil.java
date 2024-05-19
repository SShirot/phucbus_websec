package com.phucprod.csrf;

import java.security.SecureRandom;
import java.util.Base64;

public class CSRFUtil {
    private static final SecureRandom secureRandom = new SecureRandom(); //thread-safe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //thread-safe

    public static String generateCSRFToken() {
        byte[] randomBytes = new byte[32];
        
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}