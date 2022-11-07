package com.domain.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domain.models.dto.RoleRequest;
import com.domain.models.entities.Role;
import com.domain.models.repositories.RoleRepository;

@Service
@Transactional
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role save(RoleRequest roleRequest) {
        Role role = Role.build(null, roleRequest.getRole(), null);
        return roleRepository.save(role);
    }

    public List<Role> find() {
        return roleRepository.findAll();
    }
}
