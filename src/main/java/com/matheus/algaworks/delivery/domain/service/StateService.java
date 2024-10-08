package com.matheus.algaworks.delivery.domain.service;

import com.matheus.algaworks.delivery.domain.exeption.EntityInUseException;
import com.matheus.algaworks.delivery.domain.exeption.EntityNotFoundException;
import com.matheus.algaworks.delivery.domain.exeption.StateNotFoundException;
import com.matheus.algaworks.delivery.domain.model.State;
import com.matheus.algaworks.delivery.domain.repository.StateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StateService {
    private final StateRepository stateRepository;

    public State findById(Long id) {
        return this.stateRepository.findById(id)
                .orElseThrow(() -> new StateNotFoundException(id));
    }

    public State save(State state) {
        return this.stateRepository.save(state);
    }

    public State update(State state) {
        State persistedState = this.stateRepository.findById(state.getId())
                .orElseThrow(() -> new StateNotFoundException(state.getId()));
        BeanUtils.copyProperties(state, persistedState, "id");
        return this.stateRepository.save(persistedState);
    }

    public void delete(Long id) {
        if (!this.stateRepository.existsById(id)) {
            throw new StateNotFoundException(id);
        }
        try {
            this.stateRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException("Este estado já esta em uso e não pode excluido", e);
        }
    }
}
