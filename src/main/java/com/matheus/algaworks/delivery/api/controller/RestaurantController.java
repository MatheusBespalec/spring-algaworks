package com.matheus.algaworks.delivery.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matheus.algaworks.delivery.domain.exeption.EntityNotFoundException;
import com.matheus.algaworks.delivery.domain.model.Restaurant;
import com.matheus.algaworks.delivery.domain.repository.RestaurantRepository;
import com.matheus.algaworks.delivery.domain.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.management.InvalidAttributeValueException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<List<Restaurant>> list() {
        return ResponseEntity.ok(this.restaurantRepository.getAll());
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<Restaurant> find(@PathVariable Long restaurantId) {
        Restaurant restaurant = this.restaurantRepository.findById(restaurantId);
        if (restaurant == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(restaurant);
    }

    @PostMapping
    public ResponseEntity<Restaurant> save(@RequestBody Restaurant restaurant) {
        try {
            restaurant = this.restaurantService.save(restaurant);
            return ResponseEntity.status(HttpStatus.CREATED).body(restaurant);
        } catch (InvalidAttributeValueException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{restaurantId}")
    public ResponseEntity<Restaurant> replace(@PathVariable Long restaurantId, @RequestBody Restaurant restaurant) {
        restaurant.setId(restaurantId);
        try {
            restaurant = this.restaurantService.replace(restaurant);
            return ResponseEntity.ok(restaurant);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (InvalidAttributeValueException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("{restaurantId}")
    public ResponseEntity<Restaurant> update(@PathVariable Long restaurantId, @RequestBody Map<String, Object> fields) {
        Restaurant persistedRestaurant = this.restaurantRepository.findById(restaurantId);
        if (persistedRestaurant == null) {
            return ResponseEntity.notFound().build();
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Restaurant restaurant = objectMapper.convertValue(fields, Restaurant.class);
        fields.forEach((fieldName, fieldValue) -> {
            Field field = ReflectionUtils.findField(Restaurant.class, fieldName);
            if (field == null) {
                return;
            }
            field.setAccessible(true);

            fieldValue = ReflectionUtils.getField(field, restaurant);
            ReflectionUtils.setField(field, persistedRestaurant, fieldValue);
        });

        try {
            return ResponseEntity.ok(this.restaurantService.replace(persistedRestaurant));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (InvalidAttributeValueException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
