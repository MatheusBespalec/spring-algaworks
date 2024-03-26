package com.matheus.algaworks.delivery.infrastructure.repository.spec;

import com.matheus.algaworks.delivery.domain.model.Restaurant;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class RestaurantSpecs {
    public static Specification<Restaurant> freeShipping() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("freightRate"), BigDecimal.ZERO);
    }

    public static Specification<Restaurant> nameContains(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }
}
