package com.project.chatop.features.users.web.controllers;

import com.project.chatop.features.users.application.mappers.UserMapper;
import com.project.chatop.features.users.application.services.UserService;
import com.project.chatop.features.users.domain.entities.User;
import com.project.chatop.features.users.web.dtos.UserResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@Valid @Positive @NotNull @PathVariable String id) {
        User user = this.userService.getUser(Long.valueOf(id));
        return ResponseEntity.ok(userMapper.toUserResponse(user));
    }
}
