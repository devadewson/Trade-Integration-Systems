package com.maybank.integratorapp.util;

import java.lang.reflect.Method;
import java.util.Map;

public class RecursiveObjectMapper {

    public static void mapProperties(Object source, Object target, Map<String, String> propertyMapping) {
        for (Map.Entry<String, String> entry : propertyMapping.entrySet()) {
            String sourceProperty = entry.getKey();
            String targetProperty = entry.getValue();

            try {
                // Construct source getter method name (e.g., getName)
                Method getter = source.getClass().getMethod("get" + capitalize(sourceProperty));
                Object sourceValue = getter.invoke(source);

                if (sourceValue != null && isComplexType(sourceValue.getClass())) {
                    // If the value is a nested object, recursively map it
                    Object targetValue = createInstance(getter.getReturnType());
                    Method targetGetter = target.getClass().getMethod("get" + capitalize(targetProperty));
                    Object nestedTarget = targetGetter.invoke(target);

                    if (nestedTarget == null) {
                        Method targetSetter = target.getClass().getMethod("set" + capitalize(targetProperty), getter.getReturnType());
                        targetSetter.invoke(target, targetValue);
                        nestedTarget = targetValue;
                    }
                    mapProperties(sourceValue, nestedTarget, propertyMapping);
                } else {
                    // Otherwise, map the value as a simple property
                    Method setter = target.getClass().getMethod("set" + capitalize(targetProperty), getter.getReturnType());
                    setter.invoke(target, sourceValue);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean isComplexType(Class<?> clazz) {
        return !clazz.isPrimitive() && !String.class.equals(clazz) && !Number.class.isAssignableFrom(clazz);
    }

    private static Object createInstance(Class<?> clazz) throws Exception {
        return clazz.getDeclaredConstructor().newInstance();
    }

    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
