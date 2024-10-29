package com.maybank.integratorapp.util;
import java.lang.reflect.Field;
public class ReflectionUtils {
    public static void copyProperties(Object source, Object target) {
        Field[] fields = source.getClass().getDeclaredFields();

        for (Field field : fields) {
            try {
                field.setAccessible(true);  // Allow access to private fields
                Object value = field.get(source);  // Get value from source object

                Field targetField = getField(target.getClass(), field.getName()); // Get corresponding field in target
                if (targetField != null) {
                    targetField.setAccessible(true);
                    targetField.set(target, value);  // Set value in target object
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
    private static Field getField(Class<?> clazz, String fieldName) {
        while (clazz != null) {
            try {
                return clazz.getDeclaredField(fieldName);  // Look for field in current class
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();  // Try superclass if not found
            }
        }
        return null;  // Return null if field not found
    }
}
