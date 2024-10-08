package com.matheus.algaworks.delivery.domain.service;

import com.matheus.algaworks.delivery.domain.exeption.EntityNotFoundException;
import com.matheus.algaworks.delivery.domain.exeption.InvalidRelationshipException;
import com.matheus.algaworks.delivery.domain.exeption.RestaurantNotFoundException;
import com.matheus.algaworks.delivery.domain.model.Restaurant;
import com.matheus.algaworks.delivery.domain.model.RestaurantCategory;
import com.matheus.algaworks.delivery.domain.repository.RestaurantCategoryRepository;
import com.matheus.algaworks.delivery.domain.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final RestaurantCategoryRepository restaurantCategoryRepository;

    public Restaurant findById(Long id) {
        return this.restaurantRepository.findById(id).orElseThrow(() -> new RestaurantNotFoundException(id));
    }

    public Restaurant save(Restaurant restaurant) {
        Long restaurantCategoryId = restaurant.getCategory().getId();
        RestaurantCategory restaurantCategory = this.restaurantCategoryRepository.findById(restaurantCategoryId)
                .orElseThrow(() -> new InvalidRelationshipException("Categoria informada não existe"));
        restaurant.setCategory(restaurantCategory);
        return this.restaurantRepository.save(restaurant);
    }

    public Restaurant replace(Restaurant restaurant) {
        Restaurant persistedRestaurant = this.restaurantRepository.findById(restaurant.getId())
                .orElseThrow(() -> new RestaurantNotFoundException(restaurant.getId()));
        BeanUtils.copyProperties(restaurant, persistedRestaurant, "id", "paymentTypes", "address", "createdAt");
        return this.save(persistedRestaurant);
    }
}
