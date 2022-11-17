package com.domain.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.domain.models.entities.File;

@Repository
public interface FileRepository extends JpaRepository<File, String> {

}
