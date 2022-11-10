package com.domain.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.domain.exception.NotFoundException;
import com.domain.models.dto.ProjectRequest;
import com.domain.response.ResponseHandler;
import com.domain.services.ProjectService;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @PostMapping("")
    public ResponseEntity<Object> save(@Validated @RequestBody ProjectRequest projectRequest) {
        return ResponseHandler.generateResponse("success", HttpStatus.CREATED, projectService.save(projectRequest));
    }

    @GetMapping("")
    public ResponseEntity<Object> find() {
        return ResponseHandler.generateResponse("success", HttpStatus.OK, projectService.find());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findOne(@PathVariable("id") Long id) throws NotFoundException {
        return ResponseHandler.generateResponse("success", HttpStatus.OK, projectService.findOne(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") Long id, @RequestBody ProjectRequest projectRequest)
            throws NotFoundException {
        return ResponseHandler.generateResponse("success", HttpStatus.OK, projectService.update(id, projectRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> remove(@PathVariable("id") Long id) throws Exception {
        projectService.remove(id);
        return new ResponseEntity<>("Success Deleted", HttpStatus.OK);
    }
}
