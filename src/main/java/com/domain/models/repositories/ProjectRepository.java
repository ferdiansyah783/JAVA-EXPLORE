package com.domain.models.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.domain.models.entities.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByNameContains(String name);

    Project findByName(String name);
}
