package com.matheus.algaworks.delivery.domain.repository;

import com.matheus.algaworks.delivery.domain.model.City;

import java.util.List;

public interface CityRepository {
    List<City> getAll();
    City findById(Long id);
    City save(City city);
    void remove(Long id);
}
