package com.matheus.algaworks.delivery.domain.repository;

import com.matheus.algaworks.delivery.domain.model.State;

import java.util.List;

public interface StateRepository {
    List<State> getAll();
    State findById(Long id);
    State save(State state);
    void remove(Long id);
}
