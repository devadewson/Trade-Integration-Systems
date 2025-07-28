package com.maybank.integratorapp.data.utils;

import com.maybank.integratorapp.data.repository.*;
//import com.maybank.integratorapp.data.repository.GenericEntityRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class EntityService {

    @PersistenceContext
    private EntityManager entityManager;


    public <T> List<T> findAll(Class<T> entityClass) {
        String jpql = String.format("SELECT e FROM %s e", entityClass.getSimpleName());
        return entityManager.createQuery(jpql, entityClass).getResultList();
    }

    public <T> Optional<T> findById(Class<T> entityClass, Long id) {
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    public <T> T save(Class<?> entityClass, T entity) {
        if (isNewEntity(entity)) {
            entityManager.persist(entity);
            return entity;
        } else {
            return entityManager.merge(entity);
        }
    }

    public <T> void delete(Class<T> entityClass, Long id) {
        findById(entityClass, id).ifPresent(entityManager::remove);
    }

    public <T> long count(Class<T> entityClass) {
        String jpql = String.format("SELECT COUNT(e) FROM %s e", entityClass.getSimpleName());
        return entityManager.createQuery(jpql, Long.class).getSingleResult();
    }

    private <T> boolean isNewEntity(T entity) {
        try {
            Field idField = entity.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            return idField.get(entity) == null;
        } catch (Exception e) {
            throw new RuntimeException("Error checking entity status", e);
        }
    }
}