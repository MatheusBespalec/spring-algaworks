package com.matheus.algaworks.delivery.infrastructure.repository;

import com.matheus.algaworks.delivery.domain.model.Restaurant;
import com.matheus.algaworks.delivery.domain.repository.RestaurantRepositoryQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepositoryQueries {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<Restaurant> customQuery(String name, BigDecimal minFreightRate, BigDecimal maxFreightRate) {
        StringBuilder jpql = new StringBuilder("from Restaurant where 1 = 1 ");
        HashMap<String, Object> paramters = new HashMap<String, Object>();
        if (StringUtils.hasLength(name)) {
            jpql.append("and name like :name ");
            paramters.put("name", "%" + name + "%");
        }

        if (minFreightRate != null) {
            jpql.append("and freightRate >= :minFreightRate ");
            paramters.put("minFreightRate", minFreightRate);
        }

        if (maxFreightRate != null) {
            jpql.append("and freightRate <= :maxFreightRate ");
            paramters.put("maxFreightRate", maxFreightRate);
        }

        TypedQuery<Restaurant> query = this.entityManager.createQuery(jpql.toString(), Restaurant.class);
        paramters.forEach(query::setParameter);
        return query.getResultList();
    }
}
