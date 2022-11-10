package com.domain.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domain.models.dto.RoleRequest;
import com.domain.models.entities.Role;
import com.domain.models.entities.User;
import com.domain.models.repositories.RoleRepository;
import com.domain.models.repositories.UserRepository;

@Service
@Transactional
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    public Role save(Long userId, RoleRequest roleRequest) {
        Optional<User> user = userRepository.findById(userId);
        Role role = Role.build(null, roleRequest.getName(), user.get());
        return roleRepository.save(role);
    }

    public List<Role> find() {
        return roleRepository.findAll();
    }
}
