package com.domain.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.domain.exception.NotFoundException;
import com.domain.models.dto.UserRequest;
import com.domain.models.entities.User;
import com.domain.response.ResponseHandler;
import com.domain.services.UserService;

import lombok.Data;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Object> find() {
        return ResponseHandler.generateResponse("success", HttpStatus.OK, userService.find());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findOne(@PathVariable("id") Long id) throws NotFoundException {
        return ResponseHandler.generateResponse("success", HttpStatus.OK, userService.findOne(id));
    }

    @GetMapping("/pagination/{offset}/{pageSize}")
    public ResponseEntity<Object> findUserByPagination(@PathVariable("offset") int offset,
            @PathVariable("pageSize") int pageSize) {
        Page<User> allUsers = userService.findUserWithPagination(offset, pageSize);
        return ResponseHandler.generateResponse("success", HttpStatus.OK, allUsers);
    }

    @GetMapping("/user")
    public ResponseEntity<Object> findByName(@RequestParam("name") String name) throws NotFoundException {
        return ResponseHandler.generateResponse("success", HttpStatus.OK, userService.findByName(name));
    }

    @GetMapping("/user/search")
    public ResponseEntity<Object> findByNameLike(@RequestParam("name") String name) {
        return ResponseHandler.generateResponse("success", HttpStatus.OK, userService.findByNameLike(name));
    }

    @PostMapping("/auth/register")
    public ResponseEntity<Object> save(@Validated @RequestBody UserRequest userRequest) {
        return ResponseHandler.generateResponse("success", HttpStatus.CREATED,
                userService.save(userRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@RequestBody User user, @PathVariable("id") Long id) {
        return ResponseHandler.generateResponse("success", HttpStatus.OK, userService.update(user, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeOne(@PathVariable("id") Long id) throws Exception {
        userService.removeOne(id);

        return new ResponseEntity<>("Success Deleted", HttpStatus.OK);
    }

    @PostMapping("/project/user")
    public ResponseEntity<String> addProjectToUser(@RequestBody ProjectToUserForm form) {
        userService.addProjectToUser(form.getUserName(), form.getProjectName());
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}

@Data
class ProjectToUserForm {
    private String userName;
    private String projectName;
}
