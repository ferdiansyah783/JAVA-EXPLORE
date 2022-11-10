package com.domain.models.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.domain.models.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByNameContains(String name);

    Page<User> findAll(Pageable pageable);

    User findByName(String name);

    User findByUsername(String username);
}
