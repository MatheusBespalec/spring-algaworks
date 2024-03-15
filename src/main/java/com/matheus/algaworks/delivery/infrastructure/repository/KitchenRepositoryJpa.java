package com.matheus.algaworks.delivery.infrastructure.repository;

import com.matheus.algaworks.delivery.domain.model.Kitchen;
import com.matheus.algaworks.delivery.domain.repository.KitchenRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.dao.EmptyResultDataAccessException;
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
    public void remove(Long id) {
        Kitchen kitchen = this.findById(id);

        if (kitchen == null) {
            throw new EmptyResultDataAccessException(1);
        }
        entityManager.remove(kitchen);
    }
}
