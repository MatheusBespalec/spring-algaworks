package com.matheus.algaworks.jpa.infrastructure.repository;

import com.matheus.algaworks.jpa.domain.model.Kitchen;
import com.matheus.algaworks.jpa.domain.repository.KitchenRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class KitchenRepositoryJpa implements KitchenRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Kitchen findById(Long id) {
        return entityManager.find(Kitchen.class, id);
    }

    public List<Kitchen> getAll() {
        TypedQuery<Kitchen> typedQuery = entityManager.createQuery("from Kitchen", Kitchen.class);
        return typedQuery.getResultList();
    }

    @Transactional
    @Override
    public Kitchen save(Kitchen kitchen) {
        return entityManager.merge(kitchen);
    }

    @Transactional
    @Override
    public void remove(Kitchen kitchen) {
        entityManager.remove(this.findById(kitchen.getId()));
    }
}
