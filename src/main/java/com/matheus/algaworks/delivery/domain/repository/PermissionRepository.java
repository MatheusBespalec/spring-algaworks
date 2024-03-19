package com.matheus.algaworks.delivery.domain.repository;

import com.matheus.algaworks.delivery.domain.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
