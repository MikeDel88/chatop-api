package com.project.chatop.mapper;


import com.project.chatop.application.HashEncoder;
import com.project.chatop.entity.User;
import com.project.chatop.dto.request.RegisterRequest;
import com.project.chatop.dto.response.UserResponse;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class UserMapper {

    public User toUser(RegisterRequest registerRequest, HashEncoder hashEncoder) {
        if(registerRequest == null) {
            return null;
        }
        User user = new User();
        user.setEmail(registerRequest.email());
        user.setName(registerRequest.name());
        user.setPassword(hashEncoder.encode(registerRequest.password()));
        return user;
    }

    public UserResponse toUserResponse(User user) {
        if(user == null) {
            return null;
        }

        String europeanDatePattern = "yyyy/MM/dd";
        DateTimeFormatter europeanDateFormatter = DateTimeFormatter.ofPattern(europeanDatePattern);

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                europeanDateFormatter.format(user.getCreatedAt()),
                europeanDateFormatter.format(user.getUpdatedAt())
        );
    }
}
