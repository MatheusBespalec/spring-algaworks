package com.matheus.algaworks.delivery.domain.service;

import com.matheus.algaworks.delivery.domain.exeption.EntityInUseException;
import com.matheus.algaworks.delivery.domain.exeption.EntityNotFoundException;
import com.matheus.algaworks.delivery.domain.model.RestaurantCategory;
import com.matheus.algaworks.delivery.domain.repository.RestaurantCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantCategoryService {
    private final RestaurantCategoryRepository restaurantCategoryRepository;

    public RestaurantCategory save(RestaurantCategory restaurantCategory) {
        return this.restaurantCategoryRepository.save(restaurantCategory);
    }

    public void delete(Long restaurantCategoryId) {
        if (!this.restaurantCategoryRepository.existsById(restaurantCategoryId)) {
            throw new EntityNotFoundException();
        }

        try {
            this.restaurantCategoryRepository.deleteById(restaurantCategoryId);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException();
        }
    }
}
