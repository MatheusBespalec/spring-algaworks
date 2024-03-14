package com.matheus.algaworks.delivery.infrastructure.repository;

import com.matheus.algaworks.delivery.domain.model.State;
import com.matheus.algaworks.delivery.domain.repository.StateRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

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
    public State save(State state) {
        return this.entityManager.merge(state);
    }

    @Override
    public void remove(State state) {
        this.entityManager.remove(this.findById(state.getId()));
    }
}
