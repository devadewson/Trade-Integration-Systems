package com.maybank.integratorapp.util.swiftconverter;

import com.maybank.integratorapp.data.entity.SwiftMtMessage;
import com.maybank.integratorapp.data.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class MtToMxConversionService {

    @Autowired
    private SwiftMtMessageService mtMessageService;
    @Autowired
    private SwiftMtTagService mtTagService;
    @Autowired
    private SwiftConversionRuleService conversionRuleService;
    @Autowired
    private SwiftXmlTemplateService xmlTemplateService;
    @Autowired
    private SwiftMxMessageService mxMessageService;
    @Autowired
    private TemplateRenderingEngine renderingEngine;
    @Autowired
    private MappingRuleEngine mappingRuleEngine;

    @Transactional
    public void convertIncomingMessage(String rawSwiftMessage, String messageType, String srVersion) {
        try {
            // 1. INGEST: Store the raw message and get its ID
            SwiftMtMessage mtMessage = mtMessageService.createMessage(rawSwiftMessage, messageType, srVersion);
            log.info("Ingested MT"+messageType+" message. ID: {}, Ref: {}", mtMessage.getId(), mtMessage.getTransactionReference());

            // 2. PARSE: Generic parsing into tags
            mtTagService.parseAndStoreTags(mtMessage, rawSwiftMessage);
            mtMessageService.updateStatus(mtMessage.getId(), SwiftMtMessageStatus.PARSED);
            log.info("Parsed MT message into tags. ID: {}", mtMessage.getId());

            // 3. MAP: Apply rules to build the data model
            String targetMxType = determineTargetMxType(messageType); // e.g., "103" -> "pacs.008.001.08"
            Map<String, Object> mxDataModel = mappingRuleEngine.applyRules(mtMessage.getId(), messageType, targetMxType, srVersion);
            mtMessageService.updateStatus(mtMessage.getId(), SwiftMtMessageStatus.MAPPED);
            log.info("Built MX data model. ID: {}", mtMessage.getId());

            // 4. RENDER: Generate final XML
            String xmlTemplate = xmlTemplateService.getActiveTemplate(targetMxType, srVersion);
            String finalXml = renderingEngine.renderTemplate(xmlTemplate, mxDataModel);
            log.info("Rendered MX XML. ID: {}", mtMessage.getId());

            // 5. STORE & VALIDATE: Persist the output
            mxMessageService.createMxMessage(mtMessage, targetMxType, finalXml, srVersion);
            mtMessageService.updateStatus(mtMessage.getId(), SwiftMtMessageStatus.CONVERTED);
            log.info("Conversion successful. MT Message ID: {}", mtMessage.getId());

        } catch (Exception e) {
            log.error("Conversion failed for message type: {}, SR: {}", messageType, srVersion, e);
            // Update status to ERROR - implementation depends on error handling strategy
//            throw new ConversionException("Failed to convert MT message", e);
        }
    }

    private String determineTargetMxType(String mtType) {
        // This could be configurable from a database table as well
        Map<String, String> typeMap = Map.of(
                "103", "pacs.008.001.08",
                "202", "pacs.009.001.08"
                // ... other mappings
        );
        return typeMap.getOrDefault(mtType, "pacs.008.001.08"); // default
    }
}