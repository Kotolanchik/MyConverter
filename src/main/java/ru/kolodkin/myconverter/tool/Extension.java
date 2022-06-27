package ru.kolodkin.myconverter.tool;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public class Extension {
    public static boolean checkExtension(String[] str) {
        return StringUtils.contains(Arrays.toString(str), ".xml")
                && StringUtils.contains(Arrays.toString(str), ".json");
    }
}
