package com.matheus.algaworks.delivery.domain.service;

import com.matheus.algaworks.delivery.domain.exeption.EntityInUseException;
import com.matheus.algaworks.delivery.domain.exeption.EntityNotFoundException;
import com.matheus.algaworks.delivery.domain.exeption.RestaurantCategoryNotFoundException;
import com.matheus.algaworks.delivery.domain.model.RestaurantCategory;
import com.matheus.algaworks.delivery.domain.repository.RestaurantCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantCategoryService {
    private final RestaurantCategoryRepository restaurantCategoryRepository;

    public RestaurantCategory save(RestaurantCategory restaurantCategory) {
        return this.restaurantCategoryRepository.save(restaurantCategory);
    }

    public RestaurantCategory replace(RestaurantCategory restaurantCategory) {
        RestaurantCategory persistedRestaurantCategory = this.restaurantCategoryRepository.findById(restaurantCategory.getId())
                .orElseThrow(() -> new RestaurantCategoryNotFoundException(restaurantCategory.getId()));
        BeanUtils.copyProperties(restaurantCategory, persistedRestaurantCategory, "id");
        return this.save(persistedRestaurantCategory);
    }

    public void delete(Long id) {
        if (!this.restaurantCategoryRepository.existsById(id)) {
            throw new RestaurantCategoryNotFoundException(id);
        }

        try {
            this.restaurantCategoryRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException("Esta categoria não pode ser removido pois já esta por algum restaurante", e);
        }
    }

    public RestaurantCategory findById(Long id) {
        return this.restaurantCategoryRepository.findById(id)
                .orElseThrow(() -> new RestaurantCategoryNotFoundException(id));
    }
}
