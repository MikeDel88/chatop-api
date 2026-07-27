package com.project.chatop.features.users.web.controllers;

import com.project.chatop.features.users.application.services.UserService;
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

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@Valid @Positive @NotNull @PathVariable String id) {
        return ResponseEntity.ok(this.userService.getUser(Long.valueOf(id)));
    }
}
