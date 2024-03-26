package com.matheus.algaworks.delivery.domain.repository;

import com.matheus.algaworks.delivery.domain.model.Restaurant;

import java.math.BigDecimal;
import java.util.List;

public interface RestaurantRepositoryQueries {
    List<Restaurant> customQuery(String name, BigDecimal minFreightRate, BigDecimal maxFreightRate);
    List<Restaurant> findFreeShipping(String name);
}
