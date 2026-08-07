package com.project.chatop.controller;

import com.project.chatop.doc.ApiUserResponse;
import com.project.chatop.mapper.UserMapper;
import com.project.chatop.port.service.UserService;
import com.project.chatop.entity.User;
import com.project.chatop.dto.response.UserResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@Validated
@Tag(name = "Users", description = "Gestion des utilisateurs")
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }


    @ApiUserResponse
    @GetMapping("/{id}")
    public UserResponse getUser(@Positive @NotNull @PathVariable Long id) {
        log.info("call /getUser id {}", id);
        User user = this.userService.getUser(id);
        return userMapper.toUserResponse(user);
    }
}
