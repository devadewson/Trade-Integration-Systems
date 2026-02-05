package com.maybank.integratorapp.util.swiftconverter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maybank.integratorapp.data.entity.SwiftConversionRule;
import com.maybank.integratorapp.data.entity.SwiftMtTag;
import com.maybank.integratorapp.data.repository.SwiftConversionRuleRepository;
import com.maybank.integratorapp.data.repository.SwiftMtTagRepository;
import com.maybank.integratorapp.util.swiftconverter.converterfunction.FunctionRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class MappingRuleEngine {

    @Autowired
    private SwiftConversionRuleRepository conversionRuleRepository;
    @Autowired
    private SwiftMtTagRepository mtTagRepository;
    private final FunctionRegistry functionRegistry; // Contains Java methods for transformations

    public Map<String, Object> applyRules(Long mtMessageId, String sourceMtType, String targetMxType, String srVersion) {
        List<SwiftConversionRule> rules = conversionRuleRepository.findActiveRules(sourceMtType, targetMxType, srVersion);
        Map<String, Object> dataModel = new HashMap<>();
        List<SwiftMtTag> messageTags = mtTagRepository.findBySwiftMtMessageId(mtMessageId);
        log.info("Rules for "+sourceMtType+" and "+targetMxType +" : Found "+ rules.size());
        for (SwiftConversionRule rule : rules) {

            if((rule.getSourceType().equals("TAG") || rule.getSourceType().equals("FUNCTION"))){
                String _tag = rule.getSourceType().equals("FUNCTION")? rule.getInputTag() : rule.getSourceValue();
                if(messageTags.stream().noneMatch(x->x.getTagId().equals(_tag))){
                    continue;
                }
            }
            try {
                Object value = resolveRuleValue(rule, messageTags);
                if(value == null)
                    value = new HashMap<>();
                log.info("Mapping rule value for "+rule.getId()+" : {}", value);
                setValueInDataModel(dataModel, rule.getMxFieldPath(), value);
            } catch (Exception e) {
                log.warn("Failed to apply rule for path: {}", rule.getMxFieldPath(), e);
                // Decide: skip failed rule or throw exception
            }
        }
        return dataModel;
    }

    private Object resolveRuleValue(SwiftConversionRule rule, List<SwiftMtTag> messageTags) {
        return switch (rule.getSourceType()) {
            case "TAG" -> {
                String tagValue = findTagValue(messageTags, rule.getSourceValue());
                yield tagValue;
            }
            case "FUNCTION" -> {
                String tagValue = findTagValue(messageTags, rule.getInputTag()); // input_tag from rule
                yield functionRegistry.execute(rule.getSourceValue(), tagValue); // e.g., execute "splitCurrencyAndAmount"
            }
            case "MAPPING" -> {
                String tagValue = findTagValue(messageTags, rule.getSourceValue());
                Map<String, String> valueMap = parseMappingJson(rule.getMappingJson()); // e.g., {'BEN':'CRED'}
                yield valueMap.get(tagValue);
            }
            case "CONSTANT" -> rule.getConstantValue();
            default -> null;
        };
    }

    public Map<String, String> parseMappingJson(String mappingJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(mappingJson, new TypeReference<Map<String, String>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse mapping JSON", e);
        }
    }

    private String findTagValue(List<SwiftMtTag> tags, String tagId) {
        return tags.stream()
                .filter(tag -> tagId.equals(tag.getTagId()))
                .findFirst()
                .map(SwiftMtTag::getTagValue)
                .orElse(null);
    }

    private void setValueInDataModel(Map<String, Object> dataModel, String path, Object value) {
        // Complex logic to navigate the dotted path (e.g., "CdtTrfTxInf.Amt.InstructedAmount")
        // and build the nested Map structure expected by the template.
        // This can be implemented using a library like Apache Commons BeanUtils or custom logic.
        String[] keys = path.split("\\.");
        Map<String, Object> currentMap = dataModel;
        for (int i = 0; i < keys.length - 1; i++) {
            currentMap = (Map<String, Object>) currentMap.computeIfAbsent(keys[i], k -> new HashMap<String, Object>());
        }
        currentMap.put(keys[keys.length - 1], value);
    }
}