package com.matheus.algaworks.delivery.domain.exeption;

public class StateNotFoundException extends EntityNotFoundException {
    public StateNotFoundException(Long id) {
        super(String.format("O estado de id %s não foi encontrado", id));
    }

    public StateNotFoundException(Long id, Throwable cause) {
        super(String.format("O estado de id %s não foi encontrado", id), cause);
    }
}
