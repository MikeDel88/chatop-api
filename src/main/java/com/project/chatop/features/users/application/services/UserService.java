package com.project.chatop.features.users.application.services;

import com.project.chatop.features.users.application.mappers.UserMapper;
import com.project.chatop.features.users.domain.entities.User;
import com.project.chatop.features.users.domain.repositories.UserRepository;
import com.project.chatop.features.users.web.dtos.UserResponse;
import com.project.chatop.features.users.web.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserResponse getUser(Long userId) {
        User user = this.userRepository.findUserById(userId);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return this.userMapper.toUserResponse(user);
    }
}
