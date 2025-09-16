package com.maybank.integratorapp.util.swiftconverter.converterfunction;

import com.maybank.integratorapp.util.swiftconverter.customexception.TransformationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Splits a :32A: value (e.g., "230905USD1000,") into a structured object.
 */
@Component
@Slf4j
public class SplitCurrencyAndAmountFunction implements TransformationFunction {

    private final FunctionRegistry functionRegistry;

    // Inject the registry to register itself
    public SplitCurrencyAndAmountFunction(FunctionRegistry functionRegistry) {
        this.functionRegistry = functionRegistry;
    }

    // Register this function with the registry during bean initialization
    @PostConstruct
    public void init() {
        functionRegistry.registerFunction("splitCurrencyAndAmount", this);
    }

    @Override
    public Object execute(String input) {
        Map<String, String> result = new HashMap<>();

        if (input == null || input.length() < 9) { // Minimum: 6 date + 3 currency
            log.warn("Invalid input for splitCurrencyAndAmount: {}", input);
            // Create a structured object for the template
            result.put("Currency", "-");
            result.put("Amount", "-");
            return result;
        }

        try {
            // Format: Date(6) + Currency(3) + Amount
            // E.g., "230905USD1000,"
            String valueDate = input.substring(0, 6); // YYMMDD - "230905"
            String currency = input.substring(6, 9);  // "USD"
            String amountStr = input.substring(9);    // "1000,"

            // Create a structured object for the template
            result.put("Currency", currency);
            result.put("Amount", amountStr);
            // You might also want to format the date for the MX message
            // result.put("Date", formatValueDate(valueDate));

            return result;

        } catch (Exception e) {
            log.error("Error splitting currency and amount from input: {}", input, e);
            throw new TransformationException("Failed to split currency and amount");
        }
    }
}