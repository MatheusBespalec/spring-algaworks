package com.matheus.algaworks.delivery.infrastructure.repository.spec;

import com.matheus.algaworks.delivery.domain.model.Restaurant;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class RestaurantFreeShippingSpec implements Specification<Restaurant> {
    @Override
    public Predicate toPredicate(Root<Restaurant> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("freightRate"), BigDecimal.ZERO);
    }
}
