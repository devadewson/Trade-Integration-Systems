package com.maybank.integratorapp.util.swiftconverter.converterfunction;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class MapChargeBearerFunction implements TransformationFunction {

    private final FunctionRegistry functionRegistry;
    private static final Map<String, String> CHARGE_MAP = Map.of(
            "BEN", "CRED",
            "OUR", "DEBT",
            "SHA", "SHAR"
    );

    public MapChargeBearerFunction(FunctionRegistry functionRegistry) {
        this.functionRegistry = functionRegistry;
    }

    @PostConstruct
    public void init() {
        functionRegistry.registerFunction("mapChargeBearer", this);
    }

    @Override
    public Object execute(String input) {
        // Input is the raw value of :71A:, e.g., "BEN"
        if (input == null) {
            return null;
        }
        String mappedValue = CHARGE_MAP.get(input.trim());
        if (mappedValue == null) {
            log.warn("Unknown charge bearer code: {}", input);
            return null;
        }
        return mappedValue; // Returns "CRED"
    }
}
