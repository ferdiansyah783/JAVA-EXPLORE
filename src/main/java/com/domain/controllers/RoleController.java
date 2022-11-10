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

import com.domain.models.dto.RoleRequest;
import com.domain.response.ResponseHandler;
import com.domain.services.RoleService;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping("")
    public ResponseEntity<Object> save(@RequestParam("userId") Long userId,
            @Validated @RequestBody RoleRequest roleRequest) {
        return ResponseHandler.generateResponse("success created", HttpStatus.CREATED,
                roleService.save(userId, roleRequest));
    }

    @GetMapping("")
    public ResponseEntity<Object> find() {
        return ResponseHandler.generateResponse("success", HttpStatus.OK, roleService.find());
    }
}
