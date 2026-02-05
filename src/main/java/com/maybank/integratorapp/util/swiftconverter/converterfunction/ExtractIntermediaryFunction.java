package com.maybank.integratorapp.util.swiftconverter.converterfunction;

import com.maybank.integratorapp.util.swiftconverter.customexception.TransformationException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class ExtractIntermediaryFunction implements TransformationFunction {

    private final FunctionRegistry functionRegistry;

    public ExtractIntermediaryFunction(FunctionRegistry functionRegistry) {
        this.functionRegistry = functionRegistry;
    }

    @PostConstruct
    public void init() {
        functionRegistry.registerFunction("extractIntermediary", this);
    }

    @Override
    public Object execute(String input) {
        List<String> result = new ArrayList<>();

        if (input == null) { // Minimum: 6 date + 3 currency
            log.warn("Invalid input for extractIntermediary: {}", input);
            // Create a structured object for the template

            return result;
        }

        try {


            // Create a structured object for the template

            // You might also want to format the date for the MX message
            // result.put("Date", formatValueDate(valueDate));

            return result;

        } catch (Exception e) {
            log.error("Error extractIntermediary from input: {}", input, e);
            throw new TransformationException("Failed to extractIntermediary");
        }
    }
}
