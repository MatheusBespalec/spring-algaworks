package com.matheus.algaworks.delivery.domain.repository;

import com.matheus.algaworks.delivery.domain.model.Restaurant;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends CustomJpaRepository<Restaurant, Long>, RestaurantRepositoryQueries, JpaSpecificationExecutor<Restaurant> {

    @Query("from Restaurant r join fetch r.category")
    List<Restaurant> findAll();
//    @Query("from Restaurant where name like %:name% and restaurantCategory.id = :restaurantCategoryId")
    Optional<Restaurant> customSearch(String name, @Param("restaurantCategoryId") Long restaurantCategoryId);
    List<Restaurant> customQuery(String name, BigDecimal minFreightRate, BigDecimal maxFreightRate);
}
