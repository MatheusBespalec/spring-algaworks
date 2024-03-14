package com.matheus.algaworks.delivery.api.controller;

import com.matheus.algaworks.delivery.domain.model.Kitchen;
import com.matheus.algaworks.delivery.domain.repository.KitchenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/kitchens")
public class KitchenController {
    private final KitchenRepository kitchenRepository;

    @GetMapping
    public List<Kitchen> list() {
        return this.kitchenRepository.getAll();
    }

    @GetMapping("/{kitchenId}")
    public Kitchen find(@PathVariable Long kitchenId) {
        return this.kitchenRepository.findById(kitchenId);
    }
}
