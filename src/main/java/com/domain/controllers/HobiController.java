package com.domain.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.domain.models.dto.HobiRequest;
import com.domain.response.ResponseHandler;
import com.domain.services.HobiService;

@RestController
@RequestMapping("/api/hobies")
public class HobiController {
    @Autowired
    private HobiService hobiService;

    @PostMapping("")
    public ResponseEntity<Object> save(@Validated @RequestBody HobiRequest hobiRequest,
            @RequestParam("userId") Long userId) {
        return ResponseHandler.generateResponse("success created", HttpStatus.CREATED,
                hobiService.save(userId, hobiRequest));
    }

    @GetMapping("")
    public ResponseEntity<Object> find() {
        return ResponseHandler.generateResponse("success", HttpStatus.OK, hobiService.find());
    }

}
