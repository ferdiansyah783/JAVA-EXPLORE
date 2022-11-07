package com.domain.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domain.models.dto.HobiRequest;
import com.domain.models.entities.Hobi;
import com.domain.models.entities.User;
import com.domain.models.repositories.HobiRepository;
import com.domain.models.repositories.UserRepository;

@Service
@Transactional
public class HobiService {
    @Autowired
    private HobiRepository hobiRepository;

    @Autowired
    private UserRepository userRepository;

    public Hobi save(Long userId, HobiRequest hobiRequest) {
        Optional<User> user = userRepository.findById(userId);
        Hobi hobi = Hobi.build(null, hobiRequest.getHobi(), user.get());
        return hobiRepository.save(hobi);
    }

    public List<Hobi> find() {
        return hobiRepository.findAll();
    }
}
