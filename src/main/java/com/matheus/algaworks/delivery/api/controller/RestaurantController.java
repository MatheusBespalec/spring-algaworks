package com.matheus.algaworks.delivery.api.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.matheus.algaworks.delivery.domain.exeption.EntityNotFoundException;
import com.matheus.algaworks.delivery.domain.exeption.RestaurantNotFoundException;
import com.matheus.algaworks.delivery.domain.model.Restaurant;
import com.matheus.algaworks.delivery.domain.repository.RestaurantRepository;
import com.matheus.algaworks.delivery.domain.service.RestaurantService;
import com.matheus.algaworks.delivery.infrastructure.repository.spec.RestaurantSpecs;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.management.InvalidAttributeValueException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<List<Restaurant>> list() {
        return ResponseEntity.ok(this.restaurantRepository.findAll());
    }

    @GetMapping("/{restaurantId}")
    public Restaurant find(@PathVariable Long restaurantId) {
        return this.restaurantService.findById(restaurantId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurant save(@RequestBody Restaurant restaurant) {
        return this.restaurantService.save(restaurant);
    }

    @PutMapping("/{restaurantId}")
    public Restaurant replace(@PathVariable Long restaurantId, @RequestBody Restaurant restaurant) {
        restaurant.setId(restaurantId);
        return this.restaurantService.replace(restaurant);
    }

    @PatchMapping("{restaurantId}")
    public Restaurant update(@PathVariable Long restaurantId, @RequestBody Map<String, Object> fields,
                             HttpServletRequest httpServletRequest) {
        try {
            Restaurant persistedRestaurant = this.restaurantRepository.findById(restaurantId)
                    .orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
            Restaurant restaurant = objectMapper.convertValue(fields, Restaurant.class);
            fields.forEach((fieldName, fieldValue) -> {
                Field field = ReflectionUtils.findField(Restaurant.class, fieldName);
                if (field == null) {
                    return;
                }
                field.setAccessible(true);

                fieldValue = ReflectionUtils.getField(field, restaurant);
                ReflectionUtils.setField(field, persistedRestaurant, fieldValue);
            });
            return this.restaurantService.replace(persistedRestaurant);
        } catch (IllegalArgumentException e) {
            Throwable cause = ExceptionUtils.getRootCause(e);
            ServletServerHttpRequest servletServerHttpRequest = new ServletServerHttpRequest(httpServletRequest);
            throw new HttpMessageNotReadableException(e.getMessage(), cause, servletServerHttpRequest);
        }
    }
}
