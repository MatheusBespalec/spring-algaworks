package com.matheus.algaworks.delivery.domain.service;

import com.matheus.algaworks.delivery.domain.exeption.EntityInUseException;
import com.matheus.algaworks.delivery.domain.exeption.EntityNotFoundException;
import com.matheus.algaworks.delivery.domain.model.Kitchen;
import com.matheus.algaworks.delivery.domain.repository.KitchenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KitchenService {
    private final KitchenRepository kitchenRepository;

    public Kitchen save(Kitchen kitchen) {
        return this.kitchenRepository.save(kitchen);
    }

    public void delete(Long kitchenId) {
        if (!this.kitchenRepository.existsById(kitchenId)) {
            throw new EntityNotFoundException();
        }

        try {
            this.kitchenRepository.deleteById(kitchenId);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException();
        }
    }
}
