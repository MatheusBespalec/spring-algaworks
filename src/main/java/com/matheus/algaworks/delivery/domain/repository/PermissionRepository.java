package com.matheus.algaworks.delivery.domain.repository;

import com.matheus.algaworks.delivery.domain.model.Permission;

import java.util.List;

public interface PermissionRepository {
    Permission findById(Long id);
    Permission save(Permission permission);
    List<Permission> getAll(Long id);
    void remove(Permission permission);
}
