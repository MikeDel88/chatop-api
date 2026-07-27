package com.project.chatop.features.users.application.mappers;


import com.project.chatop.features.auth.application.utils.HashEncoder;
import com.project.chatop.features.users.domain.entities.User;
import com.project.chatop.features.auth.web.dtos.RegisterRequest;
import com.project.chatop.features.users.web.dtos.UserResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserMapper {

    public User toUser(RegisterRequest registerRequest, HashEncoder hashEncoder) {
        if(registerRequest == null) {
            return null;
        }

        return new User(
                null,
                registerRequest.email(),
                registerRequest.name(),
                hashEncoder.encode(registerRequest.password()),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    public UserResponse toUserResponse(User user) {
        if(user == null) {
            return null;
        }
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt().toString(),
                user.getUpdatedAt().toString()
        );
    }
}
