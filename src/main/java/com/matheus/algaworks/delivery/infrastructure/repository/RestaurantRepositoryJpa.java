package com.matheus.algaworks.delivery.infrastructure.repository;

import com.matheus.algaworks.delivery.domain.model.Restaurant;
import com.matheus.algaworks.delivery.domain.repository.RestaurantRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class RestaurantRepositoryJpa implements RestaurantRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Restaurant> getAll() {
        return this.entityManager.createQuery("from Restaurant", Restaurant.class).getResultList();
    }

    @Override
    public Restaurant findById(Long id) {
        return this.entityManager.find(Restaurant.class, id);
    }

    @Override
    @Transactional
    public Restaurant save(Restaurant restaurant) {
        return this.entityManager.merge(restaurant);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        Restaurant restaurant = this.findById(id);
        if (restaurant == null) {
            throw new EntityNotFoundException();
        }
        this.entityManager.remove(restaurant);
    }
}
