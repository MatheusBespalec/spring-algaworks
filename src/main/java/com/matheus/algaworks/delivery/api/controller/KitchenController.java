package com.matheus.algaworks.delivery.api.controller;

import com.matheus.algaworks.delivery.domain.exeption.EntityInUseException;
import com.matheus.algaworks.delivery.domain.exeption.EntityNotFoundException;
import com.matheus.algaworks.delivery.domain.model.Kitchen;
import com.matheus.algaworks.delivery.domain.repository.KitchenRepository;
import com.matheus.algaworks.delivery.domain.service.KitchenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/kitchens")
public class KitchenController {
    private final KitchenRepository kitchenRepository;
    private final KitchenService kitchenService;

    @GetMapping
    public ResponseEntity<List<Kitchen>> list() {
        return ResponseEntity.ok(this.kitchenRepository.getAll());
    }

    @GetMapping("/{kitchenId}")
    public ResponseEntity<Kitchen> find(@PathVariable Long kitchenId) {
        Kitchen kitchen = this.kitchenRepository.findById(kitchenId);

        if (kitchen == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(kitchen);
    }

    @PostMapping
    public ResponseEntity<Kitchen> save(@RequestBody Kitchen kitchen) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.kitchenRepository.save(kitchen));
    }

    @PutMapping("/{kitchenId}")
    public ResponseEntity<Kitchen> save(@PathVariable Long kitchenId, @RequestBody Kitchen kitchen) {
        Kitchen persistedKitchen = this.kitchenRepository.findById(kitchenId);
        if (persistedKitchen == null) {
            return ResponseEntity.notFound().build();
        }
        BeanUtils.copyProperties(kitchen, persistedKitchen, "id");
        return ResponseEntity.ok(this.kitchenRepository.save(persistedKitchen));
    }

    @DeleteMapping("/{kitchenId}")
    public ResponseEntity<Void> remove(@PathVariable Long kitchenId) {
        try {
            this.kitchenService.delete(kitchenId);
            return ResponseEntity.noContent().build();
        } catch (EntityInUseException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
