package com.matheus.algaworks.delivery.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class State {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue
    private Long id;
    private String name;
}
