package com.matheus.algaworks.jpa.domain.repository;

import com.matheus.algaworks.jpa.domain.model.Restaurant;

import java.util.List;

public interface RestaurantRepository {
    List<Restaurant> getAll();
    Restaurant findById(Long id);
    Restaurant save(Restaurant restaurant);
    void remove(Restaurant restaurant);

}
