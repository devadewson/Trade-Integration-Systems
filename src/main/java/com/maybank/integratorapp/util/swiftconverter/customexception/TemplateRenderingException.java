package com.maybank.integratorapp.util.swiftconverter.customexception;

import java.util.*;

public class TemplateRenderingException extends RuntimeException {

    private final String templateName;
    private final Map<String, Object> dataModel;
    private final String templateContent;

    /**
     * Constructs a new TemplateRenderingException with detailed information
     *
     * @param message the detail message
     * @param cause the root cause exception
     * @param templateName the name of the template that failed to render
     * @param dataModel the data model used for rendering
     * @param templateContent the template content that failed to render
     */
    public TemplateRenderingException(String message, Throwable cause,
                                      String templateName, Map<String, Object> dataModel,
                                      String templateContent) {
        super(message, cause);
        this.templateName = templateName;
        this.dataModel = dataModel != null ? new HashMap<>(dataModel) : null;
        this.templateContent = templateContent;
    }

    /**
     * Constructs a new TemplateRenderingException with basic information
     *
     * @param message the detail message
     * @param cause the root cause exception
     */
    public TemplateRenderingException(String message, Throwable cause) {
        this(message, cause, null, null, null);
    }

    // Getters for additional context information
    public String getTemplateName() {
        return templateName;
    }

    public Map<String, Object> getDataModel() {
        return dataModel != null ? Collections.unmodifiableMap(dataModel) : null;
    }

    public String getTemplateContent() {
        return templateContent;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        if (templateName != null) {
            sb.append(", Template: ").append(templateName);
        }
        if (templateContent != null) {
            sb.append(", Content length: ").append(templateContent.length()).append(" chars");
        }
        if (dataModel != null) {
            sb.append(", Data model keys: ").append(dataModel.keySet());
        }
        return sb.toString();
    }
}
