package com.ndimension.smartlibrary.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtils {
    public static boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public static boolean  isAlphanumeric(String str) {
        if(str.length()>=8) {
            return str.matches("^.*(?=.{8,16})(?=.*\\d)(?=.*[a-zA-Z]).*$");
        }
        else {
            return false;
        }
    }
}
