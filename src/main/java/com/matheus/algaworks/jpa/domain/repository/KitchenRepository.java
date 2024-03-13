package com.matheus.algaworks.jpa.domain.repository;

import com.matheus.algaworks.jpa.domain.model.Kitchen;

import java.util.List;

public interface KitchenRepository {
    Kitchen findById(Long id);
    List<Kitchen> getAll();
    Kitchen save(Kitchen kitchen);
    void remove(Kitchen kitchen);
}
