package com.matheus.algaworks.delivery.domain.service;

import com.matheus.algaworks.delivery.domain.exeption.CityNotFoundException;
import com.matheus.algaworks.delivery.domain.exeption.EntityInUseException;
import com.matheus.algaworks.delivery.domain.exeption.EntityNotFoundException;
import com.matheus.algaworks.delivery.domain.model.City;
import com.matheus.algaworks.delivery.domain.model.State;
import com.matheus.algaworks.delivery.domain.repository.CityRepository;
import com.matheus.algaworks.delivery.domain.repository.RestaurantRepository;
import com.matheus.algaworks.delivery.domain.repository.StateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CityService {
    private final CityRepository cityRepository;
    private final StateService stateService;
    private final RestaurantRepository restaurantRepository;

    public City save(City city) {
        State state = this.stateService.findById(city.getState().getId());
        city.setState(state);
        return this.cityRepository.save(city);
    }

    public City replace(City city) {
        City persistedCity = this.cityRepository.findById(city.getId())
                .orElseThrow(() -> new CityNotFoundException(city.getId()));
        BeanUtils.copyProperties(city, persistedCity, "id");
        return this.save(persistedCity);
    }

    public void delete(Long id) {
        if (!this.cityRepository.existsById(id)) {
            throw new CityNotFoundException(id);
        } else if (this.restaurantRepository.existsByAddressCityId(id)) {
            throw new EntityInUseException("Esta cidade não pode ser excluida pois possui um restaurante associado");
        }
        this.cityRepository.deleteById(id);
    }

    public City findById(Long id) {
        return this.cityRepository.findById(id)
                .orElseThrow(() -> new CityNotFoundException(id));
    }
}
