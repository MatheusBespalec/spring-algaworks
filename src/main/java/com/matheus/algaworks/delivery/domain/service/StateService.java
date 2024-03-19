package com.matheus.algaworks.delivery.domain.service;

import com.matheus.algaworks.delivery.domain.exeption.EntityNotFoundException;
import com.matheus.algaworks.delivery.domain.model.State;
import com.matheus.algaworks.delivery.domain.repository.StateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StateService {
    private final StateRepository stateRepository;

    public State save(State state) {
        return this.stateRepository.save(state);
    }

    public State update(State state) {
        State persistedState = this.stateRepository.findById(state.getId())
                .orElseThrow(EntityNotFoundException::new);
        BeanUtils.copyProperties(state, persistedState, "id");
        return this.stateRepository.save(persistedState);
    }

    public void delete(Long stateId) {
        if (!this.stateRepository.existsById(stateId)) {
            throw new EntityNotFoundException();
        }
        this.stateRepository.deleteById(stateId);
    }
}
