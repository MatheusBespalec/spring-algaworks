package com.matheus.algaworks.delivery.infrastructure.repository;

import com.matheus.algaworks.delivery.domain.repository.CustomJpaRepository;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

public class CustomJpaRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements CustomJpaRepository<T, ID> {
    private EntityManager entityManager;
    public CustomJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }


    @Override
    public Optional<T> findFirst() {
        String jpql = "from " + this.getDomainClass().getName();
        T entity = this.entityManager.createQuery(jpql, this.getDomainClass()).setMaxResults(1).getSingleResult();
        return Optional.ofNullable(entity);
    }
}
