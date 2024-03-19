package com.matheus.algaworks.delivery.domain.service;

import com.matheus.algaworks.delivery.domain.exeption.EntityNotFoundException;
import com.matheus.algaworks.delivery.domain.model.City;
import com.matheus.algaworks.delivery.domain.model.State;
import com.matheus.algaworks.delivery.domain.repository.CityRepository;
import com.matheus.algaworks.delivery.domain.repository.StateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.management.InvalidAttributeValueException;

@RequiredArgsConstructor
@Service
public class CityService {
    private final CityRepository cityRepository;
    private final StateRepository stateRepository;

    public City save(City city) throws InvalidAttributeValueException {
        State state = this.stateRepository.findById(city.getState().getId()).orElseThrow(InvalidAttributeValueException::new);
        city.setState(state);
        return this.cityRepository.save(city);
    }

    public City replace(City city) throws InvalidAttributeValueException {
        City persistedCity = this.cityRepository.findById(city.getId())
                .orElseThrow(EntityNotFoundException::new);
        BeanUtils.copyProperties(city, persistedCity, "id");
        return this.save(persistedCity);
    }

    public void delete(Long cityId) {
        if (!this.cityRepository.existsById(cityId)) {
            throw new EntityNotFoundException();
        }
        this.cityRepository.deleteById(cityId);
    }
}
