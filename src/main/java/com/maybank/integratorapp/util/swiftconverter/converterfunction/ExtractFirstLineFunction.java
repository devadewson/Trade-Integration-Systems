package com.maybank.integratorapp.util.swiftconverter.converterfunction;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ExtractFirstLineFunction implements TransformationFunction {

    private final FunctionRegistry functionRegistry;

    public ExtractFirstLineFunction(FunctionRegistry functionRegistry) {
        this.functionRegistry = functionRegistry;
    }

    @PostConstruct
    public void init() {
        functionRegistry.registerFunction("extractFirstLine", this);
        functionRegistry.registerFunction("extractName", this); // Alias for same logic
    }

    @Override
    public Object execute(String input) {
        // Input is the multi-line value of :50F: or :59F:
        if (input == null) {
            return "null";
        }
        // Split by newline and return the first non-empty line.
        // Often the first line is the account number or name.
        String[] lines = input.split("\\r?\\n");
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                return line.trim();
            }
        }
        return "null";
    }
}
