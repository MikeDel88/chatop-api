package com.project.chatop.features.auth.application.mappers;


import com.project.chatop.features.auth.application.utils.HashEncoder;
import com.project.chatop.features.auth.domain.entities.User;
import com.project.chatop.features.auth.web.dtos.RegisterRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserMapper {

    public User toUser(RegisterRequest registerRequest, HashEncoder hashEncoder) {
        if(registerRequest == null) {
            return null;
        }

        return new User(
                0,
                registerRequest.name(),
                registerRequest.email(),
                hashEncoder.encode(registerRequest.password()),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}
