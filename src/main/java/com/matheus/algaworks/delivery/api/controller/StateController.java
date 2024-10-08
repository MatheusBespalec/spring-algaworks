package com.matheus.algaworks.delivery.api.controller;

import com.matheus.algaworks.delivery.domain.exeption.EntityNotFoundException;
import com.matheus.algaworks.delivery.domain.model.State;
import com.matheus.algaworks.delivery.domain.repository.StateRepository;
import com.matheus.algaworks.delivery.domain.service.StateService;
import com.matheus.algaworks.delivery.infrastructure.http.api.exceptionhandler.ApiError;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/states")
@RequiredArgsConstructor
public class StateController {
    private final StateRepository stateRepository;
    private final StateService stateService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<State> list() {
        return this.stateRepository.findAll();
    }

    @GetMapping("/{stateId}")
    public State find(@PathVariable Long stateId) {
        return this.stateService.findById(stateId);
    }

    @PostMapping
    public State save(@RequestBody State state) {
        return this.stateService.save(state);
    }

    @PutMapping("/{stateId}")
    public State replace(@PathVariable Long stateId, @RequestBody State state) {
        state.setId(stateId);
        return this.stateService.update(state);
    }

    @DeleteMapping("/{stateId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long stateId) {
        this.stateService.delete(stateId);
    }
}
