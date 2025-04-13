package com.bcaf.bcapay.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import com.bcaf.bcapay.dto.ResponseDto;
import com.bcaf.bcapay.dto.UserDto;
import com.bcaf.bcapay.models.User;
import com.bcaf.bcapay.services.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Secured("FEATURE_MANAGE_USERS")
    @GetMapping
    public ResponseEntity<ResponseDto> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(new ResponseDto(200, "success", users.size() + " users found", users));
    }

    @Secured({ "FEATURE_MANAGE_USERS", "FEATURE_MANAGE_PROFILE" })
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getUserById(@PathVariable String id) {
        UserDto user = userService.getUserById(id);
        return ResponseEntity.ok(new ResponseDto(200, "success", "User found", user));
    }

    @Secured("FEATURE_MANAGE_USERS")
    @PostMapping
    public ResponseEntity<ResponseDto> createUser(@RequestBody User user) {
        UserDto createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(201, "success", "User created", createdUser));
    }

    @Secured("FEATURE_MANAGE_USERS")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> updateUser(@PathVariable String id, @RequestBody User user) {
        UserDto updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(new ResponseDto(200, "success", "User updated", updatedUser));
    }
    
    @Secured("FEATURE_MANAGE_USERS")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(new ResponseDto(200, "success", "User deleted", null));
    }
}
