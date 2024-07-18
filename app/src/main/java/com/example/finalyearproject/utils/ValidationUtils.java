package com.example.finalyearproject.utils;

import android.util.Patterns;

public class ValidationUtils {
    public static boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
