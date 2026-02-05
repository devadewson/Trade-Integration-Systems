package com.maybank.integratorapp.controller;

import com.maybank.integratorapp.data.entity.FtiAccountType;
import com.maybank.integratorapp.data.entity.MsParameter;
import com.maybank.integratorapp.data.entity.MsTBR;
import com.maybank.integratorapp.data.entity.MsTBRMapping;
import com.maybank.integratorapp.data.repository.MsParameterRepository;
import com.maybank.integratorapp.data.service.FtiAccountTypeService;
import com.maybank.integratorapp.data.service.MsParameterService;
import com.maybank.integratorapp.data.utils.*;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/settings")
public class SettingsController {
    private static Logger log = LoggerFactory.getLogger(SettingsController.class);
    @Autowired
    MsParameterService parameterService;
    private final EntityRegistryService entityRegistry;
    private final EntityService entityService;

    @Autowired
    public SettingsController(EntityRegistryService entityRegistry, EntityService entityService) {
        this.entityRegistry = entityRegistry;
        this.entityService = entityService;
    }

    @GetMapping
    public String showSettingsMenu(Model model) {
        String gsMenu = parameterService.findValueByPrmKey("GeneralSettingMenus");
        List<String> listOfClass = Arrays.stream(gsMenu.split(",")).map(x->"com.maybank.integratorapp.data.entity."+ x).toList();


//        List<String> listOfClass = Arrays.asList(
//                "com.maybank.integratorapp.data.entity.FtiAccountType",
//                "com.maybank.integratorapp.data.entity.MsCurrency"
//                // Add more entity class names as strings
//        );
        entityRegistry.initializeEntityClassNames(listOfClass);

        model.addAttribute("entities", entityRegistry.getAllEntities());
        return "layouts/settings/index";
    }

    @GetMapping("/{entityName}")
    public String manageEntity(
            @PathVariable String entityName,
            @RequestParam(required = false) Long id,
            Model model) throws Exception {

        EntityMetadata metadata = entityRegistry.getEntityMetadata(entityName);
        if (metadata == null) {
            throw new EntityNotFoundException("Entity not found");
        }

        // Get or create entity
        Object entity = id != null
                ? entityService.findById(metadata.getEntityClass(), id)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found"))
                : metadata.getEntityClass().getDeclaredConstructor().newInstance();

        model.addAttribute("metadata", metadata);
        model.addAttribute("entity", entity);
        model.addAttribute("entities", entityService.findAll(metadata.getEntityClass()));
        model.addAttribute("totalCount", entityService.count(metadata.getEntityClass()));

        return "layouts/settings/details";
    }
    @PostMapping("/{entityName}/save")
    public String saveEntity(
            @PathVariable String entityName,
            @RequestParam Map<String, String> allParams,
            @RequestParam(required = false) Long id,
            RedirectAttributes redirectAttributes) {

        EntityMetadata metadata = entityRegistry.getEntityMetadata(entityName);
        if (metadata == null) {
            throw new EntityNotFoundException("Entity not found");
        }

        try {
            // Get or create entity
            Object entity;
            if (id != null) {
                entity = entityService.findById(metadata.getEntityClass(), id)
                        .orElseThrow(() -> new EntityNotFoundException("Entity not found"));
            } else {
                entity = metadata.getEntityClass().getDeclaredConstructor().newInstance();
            }

            // Bind form data to entity dynamically
            bindParamsToEntity(allParams, entity, metadata);

            // Save the entity
            Object savedEntity = entityService.save(metadata.getEntityClass(), entity);
            redirectAttributes.addFlashAttribute("success",
                    String.format("%s saved successfully!", metadata.getDisplayName()));
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    String.format("Error saving %s: %s", metadata.getDisplayName(), e.getMessage()));
            log.error("Error saving entity", e);
        }

        return "redirect:/settings/" + entityName;
    }

    private void bindParamsToEntity(Map<String, String> params, Object entity, EntityMetadata metadata) {
        for (FieldMetadata field : metadata.getFields()) {
            try {
                String paramValue = params.get(field.getName());
                if (paramValue != null && !paramValue.isEmpty()) {
                    Field entityField = entity.getClass().getDeclaredField(field.getName());
                    entityField.setAccessible(true);


                    Object convertedValue = convertValue(paramValue, field.getType());
                    entityField.set(entity, convertedValue);
                }
            } catch (Exception e) {
                throw new RuntimeException(
                        String.format("Error binding field %s: %s", field.getName(), e.getMessage()), e);
            }
        }
    }

    private Object convertValue(String value, Class<?> targetType) {
        if (targetType == String.class) {
            return value;
        } else if (targetType == Long.class || targetType == long.class) {
            return Long.parseLong(value);
        } else if (targetType == Integer.class || targetType == int.class) {
            return Integer.parseInt(value);
        } else if (targetType == Boolean.class || targetType == boolean.class) {
            return Boolean.parseBoolean(value);
        } else if (targetType == LocalDate.class) {
            return LocalDate.parse(value);
        }
        // Add more type conversions as needed
        throw new IllegalArgumentException("Unsupported type: " + targetType.getName());
    }

//    @PostMapping("/{entityName}/save")
//    public String saveEntity(
//            @PathVariable String entityName,
//            @ModelAttribute("entity") Object entity,
//            BindingResult result,
//            RedirectAttributes redirectAttributes) {
//
//        try{
//            EntityMetadata metadata = entityRegistry.getEntityMetadata(entityName);
//            if (metadata == null) {
//                throw new EntityNotFoundException("Entity not found");
//            }
//
//            try {
//                Object savedEntity = entityService.save(metadata.getEntityClass(), entity);
//                redirectAttributes.addFlashAttribute("success",
//                        String.format("%s saved successfully!", metadata.getDisplayName()));
//            } catch (Exception e) {
//                e.printStackTrace();
//                log.error(e.toString());
//                redirectAttributes.addFlashAttribute("error",
//                        String.format("Error saving %s: %s",
//                                metadata.getDisplayName(), e.getMessage()));
//            }
//
//        }catch (Exception ex){
//            log.info("Debug6");
//
//            throw ex;
//        }
//
//        return "redirect:/settings/" + entityName;
//    }

    @PostMapping("/{entityName}/delete")
    public String deleteEntity(
            @PathVariable String entityName,
            @RequestParam Long id,
            RedirectAttributes redirectAttributes) {

        EntityMetadata metadata = entityRegistry.getEntityMetadata(entityName);
        if (metadata == null) {
            throw new EntityNotFoundException("Entity not found");
        }

        try {
            entityService.delete(metadata.getEntityClass(), id);
            redirectAttributes.addFlashAttribute("success",
                    String.format("%s deleted successfully!", metadata.getDisplayName()));
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    String.format("Error deleting %s: %s",
                            metadata.getDisplayName(), e.getMessage()));
        }
        return "redirect:/settings/" + entityName;
    }
}
