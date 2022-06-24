package ru.kolodkin.myconverter.tools;

import org.apache.commons.lang3.StringUtils;

public class Extension {
    public static boolean checkExtension(String[] str) {
        return StringUtils.contains(String.join("", str), "xml")
                && StringUtils.contains(String.join("", str), "json");
    }

    public static boolean checkExtension(String str) {
        return StringUtils.contains(str, "xml")
                && StringUtils.contains(str, "json");
    }
}
