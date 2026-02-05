package com.maybank.integratorapp.util.swiftconverter;

import com.maybank.integratorapp.util.swiftconverter.customexception.TemplateRenderingException;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

@Service
@Slf4j
public class TemplateRenderingEngine {

    @Autowired
    private freemarker.template.Configuration freeMarkerConfig; // Example using FreeMarker

    public String renderTemplate(String templateContent, Map<String, Object> dataModel) {
        try {
            freemarker.template.Configuration cfg;
            cfg = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_31);
//            cfg.setDirectoryForTemplateLoading(new File("templates"));
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            Template template = new Template("mxTemplate", new StringReader(templateContent), cfg);
            StringWriter writer = new StringWriter();
            template.process(dataModel, writer);
            return writer.toString();
        } catch (Exception e) {
            log.error("Failed to render XML template", e);
            throw new TemplateRenderingException("Template rendering failed", e);
        }
    }
}
