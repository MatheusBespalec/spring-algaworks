package com.matheus.algaworks.delivery.domain.exeption;

public class RestaurantNotFoundException extends EntityNotFoundException {
    public RestaurantNotFoundException(Long id) {
        super(String.format("O restaurante de id %s não foi encontrado", id));
    }

    public RestaurantNotFoundException(Long id, Throwable cause) {
        super(String.format("O restaurante de id %s não foi encontrado", id), cause);
    }
}
