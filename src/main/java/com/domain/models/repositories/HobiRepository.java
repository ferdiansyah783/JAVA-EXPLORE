package com.domain.models.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.domain.models.entities.Hobi;

public interface HobiRepository extends JpaRepository<Hobi, Long> {
    List<Hobi> findByHobiContains(String hobiName);
}
