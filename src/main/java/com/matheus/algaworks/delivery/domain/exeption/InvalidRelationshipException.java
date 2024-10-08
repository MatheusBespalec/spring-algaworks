package com.matheus.algaworks.delivery.domain.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidRelationshipException extends ResponseStatusException {
    public InvalidRelationshipException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
