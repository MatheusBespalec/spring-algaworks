package com.matheus.algaworks.jpa.domain.repository;

import com.matheus.algaworks.jpa.domain.model.Permission;

import java.util.List;

public interface PermissionRepository {
    Permission findById(Long id);
    Permission save(Permission permission);
    List<Permission> getAll(Long id);
    void remove(Permission permission);
}
