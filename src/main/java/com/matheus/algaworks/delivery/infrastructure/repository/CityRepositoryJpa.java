package com.matheus.algaworks.delivery.infrastructure.repository;

import com.matheus.algaworks.delivery.domain.exeption.EntityNotFoundException;
import com.matheus.algaworks.delivery.domain.model.City;
import com.matheus.algaworks.delivery.domain.repository.CityRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class CityRepositoryJpa implements CityRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<City> getAll() {
        return this.entityManager.createQuery("from City", City.class).getResultList();
    }

    @Override
    public City findById(Long id) {
        return this.entityManager.find(City.class, id);
    }

    @Override
    @Transactional
    public City save(City city) {
        return this.entityManager.merge(city);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        City city = this.findById(id);
        if (city == null) {
            throw new EntityNotFoundException();
        }
        this.entityManager.remove(city);
    }
}
