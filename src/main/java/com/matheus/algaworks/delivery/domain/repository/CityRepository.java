package com.matheus.algaworks.delivery.domain.repository;

import com.matheus.algaworks.delivery.domain.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
}
