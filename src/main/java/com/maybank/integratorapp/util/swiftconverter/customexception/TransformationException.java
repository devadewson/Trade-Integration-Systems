package com.maybank.integratorapp.util.swiftconverter.customexception;

public class TransformationException extends RuntimeException {

    private final String functionName;
    private final String input;
    private final String transformationType;

    /**
     * Constructs a new TransformationException with detailed information
     *
     * @param message the detail message
     * @param functionName the name of the transformation function that failed
     * @param input the input data that was being transformed
     * @param transformationType the type of transformation (optional)
     */
    public TransformationException(String message, String functionName, String input, String transformationType) {
        super(message);
        this.functionName = functionName;
        this.input = input;
        this.transformationType = transformationType;
    }

    /**
     * Constructs a new TransformationException with cause and detailed information
     *
     * @param message the detail message
     * @param cause the root cause exception
     * @param functionName the name of the transformation function that failed
     * @param input the input data that was being transformed
     * @param transformationType the type of transformation (optional)
     */
    public TransformationException(String message, Throwable cause, String functionName,
                                   String input, String transformationType) {
        super(message, cause);
        this.functionName = functionName;
        this.input = input;
        this.transformationType = transformationType;
    }

    /**
     * Constructs a new TransformationException for missing function
     *
     * @param message the detail message
     * @param functionName the name of the missing function
     */
    public TransformationException(String message, String functionName) {
        this(message, functionName, null, null);
    }

    public TransformationException(String message) {
        this(message, null, null, null);
    }

    // Getters for additional context information
    public String getFunctionName() {
        return functionName;
    }

    public String getInput() {
        return input;
    }

    public String getTransformationType() {
        return transformationType;
    }

    public String getInputPreview() {
        if (input == null) {
            return "null";
        }
        if (input.length() <= 50) {
            return input;
        }
        return input.substring(0, 47) + "...";
    }

    public String getInputLength() {
        return input != null ? String.valueOf(input.length()) : "0";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        if (functionName != null) {
            sb.append(", Function: ").append(functionName);
        }
        if (transformationType != null) {
            sb.append(", Type: ").append(transformationType);
        }
        if (input != null) {
            sb.append(", Input length: ").append(input.length()).append(" chars");
        }
        return sb.toString();
    }
}
