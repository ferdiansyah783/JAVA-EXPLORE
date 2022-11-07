package com.domain.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.domain.exception.UserNotFoundException;
import com.domain.models.dto.UserRequest;
import com.domain.models.entities.Role;
import com.domain.models.entities.User;
import com.domain.models.repositories.RoleRepository;
import com.domain.models.repositories.UserRepository;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public User save(String roleName, UserRequest userRequest) {
        List<Role> role = roleRepository.findByRoleContains(roleName);
        User user = User.build(null, userRequest.getName(), userRequest.getEmail(), null, role);
        return userRepository.save(user);
    }

    public User findOne(Long id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UserNotFoundException("User Not Found");
        }
    }

    public List<User> find() {
        return userRepository.findAll();
    }

    public Page<User> findUserWithPagination(int offset, int pageSize) {
        Page<User> users = userRepository.findAll(PageRequest.of(offset, pageSize));
        return users;
    }

    public User update(User user, Long id) {
        Optional<User> userData = userRepository.findById(id);
        if (userData.isPresent()) {
            User userUpdate = userData.get();
            userUpdate.setId(id);
            userUpdate.setName(user.getName());
            userUpdate.setEmail(user.getEmail());

            return userRepository.save(userUpdate);
        }

        return null;
    }

    public void removeOne(Long id) throws Exception {
        try {
            this.findOne(id);
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw e;
        }
    }

    public List<User> findByName(String name) throws UserNotFoundException {
        try {
            return userRepository.findByNameContains(name);
        } catch (Exception e) {
            throw new UserNotFoundException("User Not Found");
        }
    }
}
