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
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<List<Restaurant>> list() {
        return ResponseEntity.ok(this.restaurantRepository.findAll());
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<Restaurant> find(@PathVariable Long restaurantId) {
        Optional<Restaurant> restaurant = this.restaurantRepository.findById(restaurantId);
        return restaurant.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
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
        Optional<Restaurant> restaurantOptional = this.restaurantRepository.findById(restaurantId);
        if (restaurantOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Restaurant persistedRestaurant = restaurantOptional.get();

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

    @GetMapping("/test")
    public ResponseEntity test(String name, BigDecimal minFreightRate, BigDecimal maxFreightRate) {
        return ResponseEntity.ok(this.restaurantRepository.customQuery(name, minFreightRate, maxFreightRate));
//        return ResponseEntity.ok(this.restaurantRepository.customSearch(name, kitchenId));
    }
}
