package com.maybank.integratorapp.util.swiftconverter.converterfunction;

import com.maybank.integratorapp.util.swiftconverter.customexception.TransformationException;

/**
 * Functional interface for all transformation functions.
 * Takes a raw String input and returns a processed Object.
 */
@FunctionalInterface
public interface TransformationFunction {
    Object execute(String input) throws TransformationException;
}