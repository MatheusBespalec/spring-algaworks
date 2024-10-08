package com.matheus.algaworks.delivery.api.controller;

import com.matheus.algaworks.delivery.domain.exeption.EntityNotFoundException;
import com.matheus.algaworks.delivery.domain.model.City;
import com.matheus.algaworks.delivery.domain.repository.CityRepository;
import com.matheus.algaworks.delivery.domain.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.management.InvalidAttributeValueException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cities")
public class CityController {
    private final CityRepository cityRepository;
    private final CityService cityService;

    @GetMapping
    public List<City> list() {
        return this.cityRepository.findAll();
    }

    @GetMapping("/{cityId}")
    public City find(@PathVariable Long cityId) {
        return this.cityRepository.findById(cityId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public City save(@RequestBody City city) {
        return this.cityService.save(city);
    }

    @PutMapping("/{cityId}")
    public City replace(@PathVariable Long cityId, @RequestBody City city) {
        city.setId(cityId);
        return this.cityService.replace(city);
    }

    @DeleteMapping("/{cityId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long cityId) {
        this.cityService.delete(cityId);
    }
}
