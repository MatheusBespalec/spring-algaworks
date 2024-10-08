package com.matheus.algaworks.delivery.domain.exeption;

public class RestaurantCategoryNotFoundException extends EntityNotFoundException {
    public RestaurantCategoryNotFoundException(Long id) {
        super(String.format("A categoria de restaurante de id %s não foi encontrado", id));
    }

    public RestaurantCategoryNotFoundException(Long id, Throwable cause) {
        super(String.format("A categoria de restaurante de id %s não foi encontrado", id), cause);
    }
}
