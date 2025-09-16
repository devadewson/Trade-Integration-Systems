package com.maybank.integratorapp.util.swiftconverter.converterfunction;
import com.maybank.integratorapp.util.swiftconverter.customexception.TransformationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class FunctionRegistry {

    private final Map<String, TransformationFunction> functionMap = new HashMap<>();

    /**
     * Registers a function by its name.
     */
    public void registerFunction(String name, TransformationFunction function) {
        functionMap.put(name, function);
    }

    /**
     * Executes a function by its name with the given input.
     */
    public Object execute(String functionName, String input) {
        TransformationFunction function = functionMap.get(functionName);
        if (function == null) {
            throw new TransformationException(
                    "No function registered with name: " + functionName,
                    functionName
            );
        }

        try {
            return function.execute(input);
        } catch (Exception e) {
            log.error("Transformation failed for function: {}, Input: {}", functionName,
                    input != null ? ("length: " + input.length()) : "null", e);

            throw new TransformationException(
                    "Transformation execution failed for function: " + functionName,
                    e,
                    functionName,
                    input,
                    null // Assuming your function has a getType() method
            );
        }
    }
}
