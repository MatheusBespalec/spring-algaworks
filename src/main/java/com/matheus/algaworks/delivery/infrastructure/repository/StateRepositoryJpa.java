package com.matheus.algaworks.delivery.infrastructure.repository;

import com.matheus.algaworks.delivery.domain.exeption.EntityNotFoundException;
import com.matheus.algaworks.delivery.domain.model.State;
import com.matheus.algaworks.delivery.domain.repository.StateRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class StateRepositoryJpa implements StateRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<State> getAll() {
        return this.entityManager.createQuery("from State", State.class).getResultList();
    }

    @Override
    public State findById(Long id) {
        return this.entityManager.find(State.class, id);
    }

    @Override
    @Transactional
    public State save(State state) {
        return this.entityManager.merge(state);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        State state = this.findById(id);
        if (state == null) {
            throw new EntityNotFoundException();
        }
        this.entityManager.remove(state);
    }
}
