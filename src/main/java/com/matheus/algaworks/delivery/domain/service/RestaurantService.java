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
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final KitchenRepository kitchenRepository;
    public Restaurant save(Restaurant restaurant) throws InvalidAttributeValueException {
        Long kitchenId = restaurant.getKitchen().getId();
        Optional<Kitchen> kitchen = this.kitchenRepository.findById(kitchenId);
        if (kitchen.isEmpty()) {
            throw new InvalidAttributeValueException();
        }
        restaurant.setKitchen(kitchen.get());
        return this.restaurantRepository.save(restaurant);
    }

    public Restaurant replace(Restaurant restaurant) throws InvalidAttributeValueException {
        Restaurant persistedRestaurant = this.restaurantRepository.findById(restaurant.getId())
                .orElseThrow(EntityNotFoundException::new);

        BeanUtils.copyProperties(restaurant, persistedRestaurant, "id", "paymentTypes");
        return this.save(persistedRestaurant);
    }
}
