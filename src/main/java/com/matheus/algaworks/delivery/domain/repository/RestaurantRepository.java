package com.matheus.algaworks.delivery.domain.repository;

import com.matheus.algaworks.delivery.domain.model.Restaurant;

import java.util.List;

public interface RestaurantRepository {
    List<Restaurant> getAll();
    Restaurant findById(Long id);
    Restaurant save(Restaurant restaurant);
    void remove(Restaurant restaurant);

}
