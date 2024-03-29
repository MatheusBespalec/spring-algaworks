package com.matheus.algaworks.delivery.api.controller;

import com.matheus.algaworks.delivery.domain.exeption.EntityInUseException;
import com.matheus.algaworks.delivery.domain.exeption.EntityNotFoundException;
import com.matheus.algaworks.delivery.domain.model.RestaurantCategory;
import com.matheus.algaworks.delivery.domain.repository.RestaurantCategoryRepository;
import com.matheus.algaworks.delivery.domain.service.RestaurantCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurants/categories")
public class RestaurantCategoryController {
    private final RestaurantCategoryRepository restaurantCategoryRepository;
    private final RestaurantCategoryService restaurantCategoryService;

    @GetMapping
    public ResponseEntity<List<RestaurantCategory>> list() {
        return ResponseEntity.ok(this.restaurantCategoryRepository.findAll());
    }

    @GetMapping("/{restaurantCategoryId}")
    public ResponseEntity<RestaurantCategory> find(@PathVariable Long restaurantCategoryId) {
        Optional<RestaurantCategory> restaurantCategory = this.restaurantCategoryRepository.findById(restaurantCategoryId);
        return restaurantCategory.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/test")
    public ResponseEntity test(String name) {
        return ResponseEntity.ok(this.restaurantCategoryRepository.findByName(name));
    }

    @PostMapping
    public ResponseEntity<RestaurantCategory> save(@RequestBody RestaurantCategory restaurantCategory) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.restaurantCategoryService.save(restaurantCategory));
    }

    @PutMapping("/{restaurantCategoryId}")
    public ResponseEntity<RestaurantCategory> replace(@PathVariable Long restaurantCategoryId, @RequestBody RestaurantCategory restaurantCategory) {
        Optional<RestaurantCategory> restaurantCategoryOptional = this.restaurantCategoryRepository.findById(restaurantCategoryId);
        if (restaurantCategoryOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        RestaurantCategory persistedRestaurantCategory = restaurantCategoryOptional.get();
        BeanUtils.copyProperties(restaurantCategory, persistedRestaurantCategory, "id");
        return ResponseEntity.ok(this.restaurantCategoryService.save(persistedRestaurantCategory));
    }

    @DeleteMapping("/{restaurantCategoryId}")
    public ResponseEntity<Void> remove(@PathVariable Long restaurantCategoryId) {
        try {
            this.restaurantCategoryService.delete(restaurantCategoryId);
            return ResponseEntity.noContent().build();
        } catch (EntityInUseException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
