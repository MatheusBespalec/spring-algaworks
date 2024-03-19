package com.matheus.algaworks.delivery.api.controller;

import com.matheus.algaworks.delivery.domain.exeption.EntityNotFoundException;
import com.matheus.algaworks.delivery.domain.model.City;
import com.matheus.algaworks.delivery.domain.repository.CityRepository;
import com.matheus.algaworks.delivery.domain.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.InvalidAttributeValueException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cities")
public class CityController {
    private final CityRepository cityRepository;
    private final CityService cityService;

    @GetMapping
    public ResponseEntity<List<City>> list() {
        List<City> cities = this.cityRepository.findAll();
        return ResponseEntity.ok(cities);
    }

    @GetMapping("/{cityId}")
    public ResponseEntity<City> find(@PathVariable Long cityId) {
        return this.cityRepository.findById(cityId)
                .map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<City> save(@RequestBody City city) {
        try {
            city = this.cityService.save(city);
            return ResponseEntity.ok(city);
        } catch (InvalidAttributeValueException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{cityId}")
    public ResponseEntity<City> replace(@PathVariable Long cityId, @RequestBody City city) {
        city.setId(cityId);
        try {
            city = this.cityService.replace(city);
            return ResponseEntity.ok(city);
        } catch (InvalidAttributeValueException e) {
            return ResponseEntity.badRequest().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{cityId}")
    public ResponseEntity<Void> delete(@PathVariable Long cityId) {
        try {
            this.cityService.delete(cityId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
