package com.matheus.algaworks.delivery.domain.service;

import com.matheus.algaworks.delivery.domain.exeption.EntityNotFoundException;
import com.matheus.algaworks.delivery.domain.model.Kitchen;
import com.matheus.algaworks.delivery.domain.model.Restaurant;
import com.matheus.algaworks.delivery.domain.repository.KitchenRepository;
import com.matheus.algaworks.delivery.domain.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.management.InvalidAttributeValueException;

@RequiredArgsConstructor
@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final KitchenRepository kitchenRepository;
    public Restaurant save(Restaurant restaurant) throws InvalidAttributeValueException {
        Long kitchenId = restaurant.getKitchen().getId();
        Kitchen kitchen = this.kitchenRepository.findById(kitchenId);
        if (kitchen == null) {
            throw new InvalidAttributeValueException();
        }
        restaurant.setKitchen(kitchen);
        return this.restaurantRepository.save(restaurant);
    }

    public Restaurant replace(Restaurant restaurant) throws InvalidAttributeValueException {
        Restaurant persistedRestaurant = this.restaurantRepository.findById(restaurant.getId());
        if (persistedRestaurant == null) {
            throw new EntityNotFoundException();
        }

        BeanUtils.copyProperties(restaurant, persistedRestaurant, "id");
        return this.save(persistedRestaurant);
    }
}
