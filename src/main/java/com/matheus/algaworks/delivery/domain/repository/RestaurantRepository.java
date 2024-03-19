package com.matheus.algaworks.delivery.domain.repository;

import com.matheus.algaworks.delivery.domain.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
