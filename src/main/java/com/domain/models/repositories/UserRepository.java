package com.domain.models.repositories;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.domain.models.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByNameContains(String name);

    Page<User> findAll(Pageable pageable);

    User findByName(String name);

    User findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.name LIKE :name")
    public List<User> findByNameLike(@PathParam("name") String name);
}
