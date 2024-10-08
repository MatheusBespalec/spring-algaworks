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
    public List<RestaurantCategory> list() {
        return this.restaurantCategoryRepository.findAll();
    }

    @GetMapping("/{restaurantCategoryId}")
    public RestaurantCategory find(@PathVariable Long restaurantCategoryId) {
        return this.restaurantCategoryService.findById(restaurantCategoryId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantCategory save(@RequestBody RestaurantCategory restaurantCategory) {
        return this.restaurantCategoryService.save(restaurantCategory);
    }

    @PutMapping("/{restaurantCategoryId}")
    public RestaurantCategory replace(@PathVariable Long restaurantCategoryId, @RequestBody RestaurantCategory restaurantCategory) {
        restaurantCategory.setId(restaurantCategoryId);
        return this.restaurantCategoryService.replace(restaurantCategory);
    }

    @DeleteMapping("/{restaurantCategoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long restaurantCategoryId) {
            this.restaurantCategoryService.delete(restaurantCategoryId);
    }
}
