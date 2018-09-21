package com.example.demo.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
    public static boolean positiveInteger(String text){
        if(text == null || text.isEmpty() == true){
            return false;
        }
        String regEx1 = "\\d+";
        Pattern p;
        Matcher m;
        p = Pattern.compile(regEx1);
        m = p.matcher(text);
        if (m.matches())
            return true;
        else
            return false;
    }
}
