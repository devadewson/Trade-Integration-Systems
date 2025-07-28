package com.maybank.integratorapp.data.utils;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import com.maybank.integratorapp.data.entity.*;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
@Service
public class EntityRegistryService {
    private Map<String, EntityMetadata> entityRegistry = new HashMap<>();
    private Set<String> entityClassNames = new HashSet<>();
//    private final ApplicationContext applicationContext;
//
//    public EntityRegistryService(ApplicationContext applicationContext) {
//        this.applicationContext = applicationContext;
//        initializeEntityClassNames();
//    }

    public void initializeEntityClassNames(List<String> listOfClass) {
        // Add your entity class names here or load from configuration
        entityRegistry.clear();
        entityClassNames.clear();
        entityClassNames.addAll(listOfClass);
    }

    public void registerEntity(String name, String displayName, Class<?> entityClass,
                               List<FieldMetadata> fields) {
        EntityMetadata metadata = new EntityMetadata();
        metadata.setName(name);
        metadata.setDisplayName(displayName);
        metadata.setEntityClass(entityClass);
        metadata.setFields(fields);
        entityRegistry.put(name, metadata);
    }

    public void registerEntityFromClassName(String className) {
        try {
            Class<?> entityClass = Class.forName(className);
            String simpleName = entityClass.getSimpleName();
            String entityName = StringUtils.uncapitalize(simpleName);

            List<FieldMetadata> fields = new ArrayList<>();
            for (Field field : entityClass.getDeclaredFields()) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    fields.add(new FieldMetadata(
                            field.getName(),
                            StringUtils.capitalize(field.getName()),
                            field.getType(),
                            false
                    ));
                }
            }

            registerEntity(entityName, simpleName, entityClass, fields);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load entity class: " + className, e);
        }
    }


    public void registerAllEntities() {
        entityClassNames.forEach(this::registerEntityFromClassName);
    }

    public EntityMetadata getEntityMetadata(String entityName) {
        return entityRegistry.get(entityName);
    }

    public Collection<EntityMetadata> getAllEntities() {
        if (entityRegistry.isEmpty()) {
            registerAllEntities();
        }
        return entityRegistry.values();
    }
}