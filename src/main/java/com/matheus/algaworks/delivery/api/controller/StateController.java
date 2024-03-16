package com.matheus.algaworks.delivery.api.controller;

import com.matheus.algaworks.delivery.domain.exeption.EntityNotFoundException;
import com.matheus.algaworks.delivery.domain.model.State;
import com.matheus.algaworks.delivery.domain.repository.StateRepository;
import com.matheus.algaworks.delivery.domain.service.StateService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping
    public ResponseEntity<List<State>> list() {
        List<State> states = this.stateRepository.getAll();
        return ResponseEntity.ok(states);
    }

    @GetMapping("/{stateId}")
    public ResponseEntity<State> find(@PathVariable Long stateId) {
        State state = this.stateRepository.findById(stateId);
        if (state == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(state);
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
        }
    }
}
