package com.domain.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domain.exception.NotFoundException;
import com.domain.models.dto.AbsenRequest;
import com.domain.models.entities.Absen;
import com.domain.models.entities.User;
import com.domain.models.repositories.AbsenRepository;
import com.domain.models.repositories.UserRepository;

@Service
@Transactional
public class AbsenService {
    @Autowired
    private AbsenRepository absenRepository;

    @Autowired
    private UserRepository userRepository;

    public Absen save(AbsenRequest absenRequest) throws NotFoundException {
        User userDb = userRepository.findByUsername(absenRequest.getUsername());

        if (userDb == null) {
            throw new NotFoundException("User Not Found");
        }

        Absen absen = new Absen();
        absen.setId(userDb.getId());
        absen.setUsername(userDb.getUsername());

        return absenRepository.save(absen);
    }

    public List<Absen> find() {
        return absenRepository.findAll();
    }
}
