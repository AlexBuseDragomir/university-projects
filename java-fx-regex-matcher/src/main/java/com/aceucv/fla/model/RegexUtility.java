package com.aceucv.fla.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtility {

    public static boolean doesStringMatchRegex(String toCheck, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(toCheck);
        return matcher.matches();
    }
}
