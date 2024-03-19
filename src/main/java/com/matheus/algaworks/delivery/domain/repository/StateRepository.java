package com.matheus.algaworks.delivery.domain.repository;

import com.matheus.algaworks.delivery.domain.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {
}
