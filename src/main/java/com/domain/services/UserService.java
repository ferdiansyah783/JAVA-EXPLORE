package com.domain.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.domain.exception.NotFoundException;
import com.domain.models.dto.UserRequest;
import com.domain.models.entities.Project;
import com.domain.models.entities.User;
import com.domain.models.repositories.ProjectRepository;
import com.domain.models.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User Not Found");
        } else {
            log.info("User Found : {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getUsername()));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                authorities);
    }

    public User save(UserRequest userRequest) {
        User userExist = userRepository.findByUsername(userRequest.getUsername());

        if (userExist != null) {
            throw new RuntimeException(String.format("Username '%s' Already Used",
                    userRequest.getUsername()));
        }

        String encodePassword = bCryptPasswordEncoder.encode(userRequest.getPassword());
        userRequest.setPassword(encodePassword);
        User user = modelMapper.map(userRequest, User.class);
        return userRepository.save(user);
    }

    public void addProjectToUser(String userName, String projectName) {
        User user = userRepository.findByUsername(userName);
        Project project = projectRepository.findByName(projectName);
        user.getProjects().add(project);
    }

    public User findOne(Long id) throws NotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new NotFoundException("User Not Found");
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

    public List<User> findByName(String name) throws NotFoundException {
        try {
            return userRepository.findByNameContains(name);
        } catch (Exception e) {
            throw new NotFoundException("User Not Found");
        }
    }

    public List<User> findByNameLike(String name) {
        return userRepository.findByNameLike("%" + name + "%");
    }
}
