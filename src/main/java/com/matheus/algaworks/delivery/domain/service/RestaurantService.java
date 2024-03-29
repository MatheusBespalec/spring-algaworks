package com.matheus.algaworks.delivery.domain.service;

import com.matheus.algaworks.delivery.domain.exeption.EntityNotFoundException;
import com.matheus.algaworks.delivery.domain.model.Restaurant;
import com.matheus.algaworks.delivery.domain.model.RestaurantCategory;
import com.matheus.algaworks.delivery.domain.repository.RestaurantCategoryRepository;
import com.matheus.algaworks.delivery.domain.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.management.InvalidAttributeValueException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final RestaurantCategoryRepository restaurantCategoryRepository;
    public Restaurant save(Restaurant restaurant) throws InvalidAttributeValueException {
        Long restaurantCategoryId = restaurant.getCategory().getId();
        Optional<RestaurantCategory> restaurantCategory = this.restaurantCategoryRepository.findById(restaurantCategoryId);
        if (restaurantCategory.isEmpty()) {
            throw new InvalidAttributeValueException();
        }
        restaurant.setCategory(restaurantCategory.get());
        return this.restaurantRepository.save(restaurant);
    }

    public Restaurant replace(Restaurant restaurant) throws InvalidAttributeValueException {
        Restaurant persistedRestaurant = this.restaurantRepository.findById(restaurant.getId())
                .orElseThrow(EntityNotFoundException::new);

        BeanUtils.copyProperties(restaurant, persistedRestaurant, "id", "paymentTypes", "address", "createdAt");
        return this.save(persistedRestaurant);
    }
}
