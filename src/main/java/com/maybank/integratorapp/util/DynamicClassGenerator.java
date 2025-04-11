package com.maybank.integratorapp.util;

//import javassist.*;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.matcher.ElementMatchers;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DynamicClassGenerator {

//    public static Class<?> generateClass(String className, List<String> propertyNames) throws Exception {
//        // Create a new class with the given name
//        ClassPool pool = ClassPool.getDefault();
//        CtClass ctClass = pool.makeClass(className);
//
//        // Iterate through property names to create fields and methods
//        for (String property : propertyNames) {
//            // Create a private String field
//            CtField ctField = new CtField(pool.get("java.lang.String"), property, ctClass);
//            ctField.setModifiers(Modifier.PRIVATE);
//            ctClass.addField(ctField);
//
//            // Create a getter method for the field
//            String getterName = "get" + capitalize(property);
//            CtMethod getterMethod = CtNewMethod.make(
//                    "public String " + getterName + "() { return this." + property + "; }", ctClass);
//            ctClass.addMethod(getterMethod);
//
//            // Create a setter method for the field
//            String setterName = "set" + capitalize(property);
//            CtMethod setterMethod = CtNewMethod.make(
//                    "public void " + setterName + "(String " + property + ") { this." + property + " = " + property + "; }", ctClass);
//            ctClass.addMethod(setterMethod);
//        }
//
//        // Return the generated class
//        return ctClass.toClass();
//    }

//    public static Class<?> generateClass(String className, List<DynamicClassPropertyMap> propertyNames) throws Exception {
//        ByteBuddy byteBuddy = new ByteBuddy();
//        DynamicType.Builder<?> builder = byteBuddy.subclass(Object.class).name(className);
//
//        // Iterate through property names to create fields and methods
//        for (var property : propertyNames) {
//            builder = builder
//                    .defineField(property.getFieldName(), property.getFieldType().equals("Integer")?Integer.class:String.class, Modifier.PRIVATE)
//                    .defineMethod("get" + capitalize(property.getFieldName()), property.getFieldType().equals("Integer")?Integer.class:String.class, Modifier.PUBLIC)
//                    .intercept(FieldAccessor.ofField(property.getFieldName()))
//                    .defineMethod("set" + capitalize(property.getFieldName()), void.class, java.lang.reflect.Modifier.PUBLIC)
//                    .withParameter(property.getFieldType().equals("Integer")?Integer.class:String.class)
//                    .intercept(FieldAccessor.ofField(property.getFieldName()));
//        }
//
//        // Create the dynamic class
//        return builder.make().load(DynamicClassGenerator.class.getClassLoader()).getLoaded();
//    }
    public static Class<?> generateClass(String className, List<DynamicClassPropertyMap> propertyNames) throws Exception {
        ByteBuddy byteBuddy = new ByteBuddy();
        DynamicType.Builder<?> builder = byteBuddy.subclass(Object.class).name(className);

        // First pass: collect all existing field names to avoid duplicates
        Set<String> existingFields = new HashSet<>();

        // Second pass: define fields and methods
        for (DynamicClassPropertyMap property : propertyNames) {
            String fieldName = property.getFieldName();
            Class<?> fieldType = property.getFieldType().equals("Integer") ? Integer.class : String.class;
            String capitalizedName = capitalize(fieldName);
            String getterName = "get" + capitalizedName;
            String setterName = "set" + capitalizedName;

            // Skip if field already exists
            if (existingFields.contains(fieldName)) {
                continue;
            }
            existingFields.add(fieldName);

            // Define the field
            builder = builder.defineField(fieldName, fieldType, Modifier.PRIVATE);

            // Define getter if it doesn't exist
            try {
                builder = builder.defineMethod(getterName, fieldType, Modifier.PUBLIC)
                        .intercept(FieldAccessor.ofField(fieldName));
            } catch (IllegalStateException e) {
                // Getter already exists, skip
            }

            // Define setter if it doesn't exist
            try {
                builder = builder.defineMethod(setterName, void.class, Modifier.PUBLIC)
                        .withParameter(fieldType, fieldName)
                        .intercept(FieldAccessor.ofField(fieldName));
            } catch (IllegalStateException e) {
                // Setter already exists, skip
            }
        }

        return builder.make()
                .load(DynamicClassGenerator.class.getClassLoader())
                .getLoaded();
    }

    // Utility method to capitalize the first letter of a string
    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }


//    public static void main(String[] args) throws Exception {
//        List<String> propertyNames = List.of("name", "age", "email");
//
//        // Generate the dynamic class
//        Class<?> dynamicClass = generateClass("Person", propertyNames);
//
//        // Create an instance of the dynamically generated class
//        Object instance = dynamicClass.getDeclaredConstructor().newInstance();
//
//        // Use reflection to set and get properties
//        Method setName = dynamicClass.getMethod("setName", String.class);
//        Method getName = dynamicClass.getMethod("getName");
//
//        setName.invoke(instance, "John Doe");
//        String name = (String) getName.invoke(instance);
//
//        System.out.println("Name: " + name);
//    }
}
