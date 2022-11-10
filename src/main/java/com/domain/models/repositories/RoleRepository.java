package com.domain.models.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.domain.models.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findByNameContains(String name);

    Role findByName(String name);
}
