package com.maybank.integratorapp.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.maybank.integratorapp.model.rest.compositetbr.request.ExecuteCompositeTransactionRequest;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class DynamicTBRJsonSerializer extends JsonSerializer<ExecuteCompositeTransactionRequest> {
    @Override
    public void serialize(ExecuteCompositeTransactionRequest wrapper, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        // Start writing the object
        gen.writeStartObject();

        // Dynamically get the class name of the dynamicPersonInstances
        List<Object> dynamicObjects = wrapper.getTBRData();
        if (dynamicObjects != null && !dynamicObjects.isEmpty()) {
            String dynamicClassName = dynamicObjects.get(0).getClass().getSimpleName(); // Assume all are of the same type

            // Write the dynamic class properties under the dynamic class name as the key
            gen.writeArrayFieldStart(dynamicClassName);

            // Loop through all dynamic object instances and serialize them
            for (Object dynamicObject : dynamicObjects) {
                gen.writeStartObject(); // Start each dynamic object object
                // Loop through all fields of the dynamic class and serialize them
                for (Field field : dynamicObject.getClass().getDeclaredFields()) {
                    try {
                        field.setAccessible(true); // Make private fields accessible
                        String fieldName = field.getName();
                        Object fieldValue = field.get(dynamicObject);

                        gen.writeObjectField(fieldName, fieldValue); // Write each field as a key-value pair
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                gen.writeEndObject(); // End each dynamic object object
            }
            gen.writeEndArray(); // End the array of dynamic object
        }

        // Now, serialize all fields of PersonWrapper itself
        for (Field field : ExecuteCompositeTransactionRequest.class.getDeclaredFields()) {
            try {
                field.setAccessible(true); // Make private fields accessible
                String fieldName = field.getName();
                if(!fieldName.equals("TBRData")){
                    Object fieldValue = field.get(wrapper); // Get value from the wrapper

                    gen.writeObjectField(fieldName, fieldValue); // Write each field as a key-value pair
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        gen.writeEndObject(); // End the wrapper object
    }

//    @Override
//    public void serialize(ChannelHeader channelHeader, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
//
//    }
}
