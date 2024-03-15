package com.matheus.algaworks.delivery.domain.repository;

import com.matheus.algaworks.delivery.domain.model.Kitchen;

import java.util.List;

public interface KitchenRepository {
    Kitchen findById(Long id);
    List<Kitchen> getAll();
    Kitchen save(Kitchen kitchen);
    void remove(Long id);
}
