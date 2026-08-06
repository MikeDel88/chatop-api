package com.project.chatop.mapper;


import com.project.chatop.security.HashEncoder;
import com.project.chatop.entity.User;
import com.project.chatop.dto.request.RegisterRequest;
import com.project.chatop.dto.response.UserResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Log4j2
@Component
public class UserMapper {

    public User toUser(RegisterRequest registerRequest, HashEncoder hashEncoder) {
        log.info("toUser {}", registerRequest);
        log.debug("toUser {}", hashEncoder);

        if(registerRequest == null) {
            return null;
        }
        User user = new User();
        user.setEmail(registerRequest.email());
        user.setName(registerRequest.name());
        user.setPassword(hashEncoder.encode(registerRequest.password()));
        log.debug("toUser : {}", user);
        return user;
    }

    public UserResponse toUserResponse(User user) {
        log.info("toUserResponse : {}", user);
        if(user == null) {
            return null;
        }

        String europeanDatePattern = "yyyy/MM/dd";
        DateTimeFormatter europeanDateFormatter = DateTimeFormatter.ofPattern(europeanDatePattern);

        UserResponse userResponse = new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                europeanDateFormatter.format(user.getCreatedAt()),
                europeanDateFormatter.format(user.getUpdatedAt())
        );
        log.debug("toUserResponse : {}", userResponse);
        return userResponse;
    }
}
