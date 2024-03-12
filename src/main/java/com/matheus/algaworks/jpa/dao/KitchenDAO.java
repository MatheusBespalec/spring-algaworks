package com.matheus.algaworks.jpa.dao;

import com.matheus.algaworks.jpa.domain.model.Kitchen;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KitchenDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Kitchen> getAll() {
        TypedQuery<Kitchen> typedQuery = entityManager.createQuery("from Kitchen", Kitchen.class);
        return typedQuery.getResultList();
    }
}
