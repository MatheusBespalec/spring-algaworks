package com.matheus.algaworks.jpa.jpa.domain.model;

import jakarta.persistence.*;

@Entity
public class Kitchen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
