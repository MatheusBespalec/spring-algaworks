package com.matheus.algaworks.delivery.domain.exeption;

public class CityNotFoundException extends EntityNotFoundException {
    public CityNotFoundException(Long id) {
        super(String.format("A cidade de id %s não foi encontrado", id));
    }

    public CityNotFoundException(Long id, Throwable cause) {
        super(String.format("A cidade de id %s não foi encontrado", id), cause);
    }
}
