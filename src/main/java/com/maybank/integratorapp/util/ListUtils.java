package com.maybank.integratorapp.util;

import java.util.*;

public class ListUtils {

    public static <T> T getOrDefault(List<T> list, int index, T defaultValue) {
        try {
            return list.get(index);
        } catch (IndexOutOfBoundsException e) {
            return defaultValue;
        }
    }

    public static <T> T getOrNull(List<T> list, int index) {
        return getOrDefault(list, index, null);
    }

}
