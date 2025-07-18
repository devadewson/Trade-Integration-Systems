package com.maybank.integratorapp.util;

import java.lang.reflect.Field;

public class ReflectionUtils {
    public static void copyProperties(Object source, Object target) {
        // Get all fields from source (including superclass fields)
        Field[] sourceFields = getAllFields(source.getClass());

        for (Field sourceField : sourceFields) {
            try {
                sourceField.setAccessible(true);  // Allow access to private fields
                Object value = sourceField.get(source);  // Get value from source

                // Find corresponding field in target (including superclasses)
                Field targetField = findField(target.getClass(), sourceField.getName());

                if (targetField != null) {
                    targetField.setAccessible(true);
                    targetField.set(target, value);  // Set value in target
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Gets all fields of a class (including superclass fields)
     */
    private static Field[] getAllFields(Class<?> clazz) {
        java.util.List<Field> fields = new java.util.ArrayList<>();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                fields.add(field);
            }
            clazz = clazz.getSuperclass(); // Move up to the superclass
        }
        return fields.toArray(new Field[0]);
    }

    /**
     * Finds a field in a class or its superclasses
     */
    private static Field findField(Class<?> clazz, String fieldName) {
        while (clazz != null) {
            try {
                return clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass(); // Check superclass if not found
            }
        }
        return null; // Field not found
    }
}