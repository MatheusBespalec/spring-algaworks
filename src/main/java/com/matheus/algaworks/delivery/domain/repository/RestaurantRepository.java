package com.matheus.algaworks.delivery.domain.repository;

import com.matheus.algaworks.delivery.domain.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long>, RestaurantRepositoryQueries, JpaSpecificationExecutor<Restaurant> {

//    @Query("from Restaurant where name like %:name% and kitchen.id = :kitchenId")
    Optional<Restaurant> customSearch(String name, @Param("kitchenId") Long kitchen);
    List<Restaurant> customQuery(String name, BigDecimal minFreightRate, BigDecimal maxFreightRate);
}
