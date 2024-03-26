package com.matheus.algaworks.delivery.infrastructure.repository;

import com.matheus.algaworks.delivery.domain.model.Restaurant;
import com.matheus.algaworks.delivery.domain.repository.RestaurantRepository;
import com.matheus.algaworks.delivery.domain.repository.RestaurantRepositoryQueries;
import com.matheus.algaworks.delivery.infrastructure.repository.spec.RestaurantSpecs;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepositoryQueries {
    @PersistenceContext
    private EntityManager entityManager;

    private final RestaurantRepository restaurantRepository;

    public RestaurantRepositoryImpl(@Lazy RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }
    @Override
    public List<Restaurant> customQuery(String name, BigDecimal minFreightRate, BigDecimal maxFreightRate) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Restaurant> criteriaQuery = criteriaBuilder.createQuery(Restaurant.class);

        Root<Restaurant> root = criteriaQuery.from(Restaurant.class);

        ArrayList<Predicate> predicates = new ArrayList<Predicate>();

        if (StringUtils.hasLength(name)) {
            predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
        }

        if (minFreightRate != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("freightRate"), minFreightRate));
        }

        if (maxFreightRate != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("freightRate"), maxFreightRate));
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Restaurant> query = this.entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public List<Restaurant> findFreeShipping(String name) {
        return this.restaurantRepository.findAll(RestaurantSpecs.freeShipping()
                .and(RestaurantSpecs.nameContains(name)));
    }
}
