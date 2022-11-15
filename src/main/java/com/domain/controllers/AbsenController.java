package com.domain.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.domain.exception.NotFoundException;
import com.domain.models.dto.AbsenRequest;
import com.domain.response.ResponseHandler;
import com.domain.services.AbsenService;

@RestController
@RequestMapping("/api/absen")
public class AbsenController {
    @Autowired
    private AbsenService absenService;

    @PostMapping("")
    public ResponseEntity<Object> save(@Validated @RequestBody AbsenRequest absenRequest) throws NotFoundException {
        return ResponseHandler.generateResponse("success", HttpStatus.CREATED, absenService.save(absenRequest));
    }

    @GetMapping("")
    public ResponseEntity<Object> find() {
        return ResponseHandler.generateResponse("success", HttpStatus.OK, absenService.find());
    }
}
