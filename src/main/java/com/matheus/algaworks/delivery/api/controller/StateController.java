package com.matheus.algaworks.delivery.api.controller;

import com.matheus.algaworks.delivery.domain.model.State;
import com.matheus.algaworks.delivery.domain.repository.StateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/states")
@RequiredArgsConstructor
public class StateController {
    private final StateRepository stateRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<State> list() {
        return stateRepository.getAll();
    }
}
