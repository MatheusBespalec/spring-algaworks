package com.matheus.algaworks.delivery.api.controller;

import com.matheus.algaworks.delivery.domain.exeption.EntityNotFoundException;
import com.matheus.algaworks.delivery.domain.model.State;
import com.matheus.algaworks.delivery.domain.repository.StateRepository;
import com.matheus.algaworks.delivery.domain.service.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/states")
@RequiredArgsConstructor
public class StateController {
    private final StateRepository stateRepository;
    private final StateService stateService;

    @GetMapping
    public ResponseEntity<List<State>> list() {
        List<State> states = this.stateRepository.findAll();
        return ResponseEntity.ok(states);
    }

    @GetMapping("/{stateId}")
    public ResponseEntity<State> find(@PathVariable Long stateId) {
        return this.stateRepository.findById(stateId)
                .map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<State> save(@RequestBody State state) {
        state = this.stateService.save(state);
        return ResponseEntity.ok(state);
    }

    @PutMapping("/{stateId}")
    public ResponseEntity<State> replace(@PathVariable Long stateId, @RequestBody State state) {
        state.setId(stateId);
        try {
            state = this.stateService.update(state);
            return ResponseEntity.ok(state);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{stateId}")
    public ResponseEntity<Void> delete(@PathVariable Long stateId) {
        try {
            this.stateService.delete(stateId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
